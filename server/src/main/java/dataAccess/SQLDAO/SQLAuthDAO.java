package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() throws ResponseException {
        InitializeDAO initializeDAO = new InitializeDAO();
        initializeDAO.initialize(createStatements);
    }
    @Override
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        UpdateTable table = new UpdateTable();
        var statement = "INSERT INTO authorization (authToken, username) VALUES (?, ?)";
        try {
            table.executeUpdate(statement, authData.authToken(), authData.username());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
        return authData;
    }

    @Override
    public AuthData getAuth(String authToken) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM authorization WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
//            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
            return null;
        }
        return null;
    }

    @Override
    public String getToken(String username) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken FROM authorization WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readToken(rs);
                    }
                }
            }
        } catch (Exception e) {
//            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
            return null;
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        UpdateTable table = new UpdateTable();
        var statement = "DELETE FROM authorization WHERE authToken=?";
        try {
            table.executeUpdate(statement, authToken);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAuths() {
        UpdateTable table = new UpdateTable();
        var statement = "TRUNCATE authorization";
        try {
            table.executeUpdate(statement);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        String authToken = rs.getString("authToken");
        String username = rs.getString("username");
        return new AuthData(authToken, username);
    }

    private String readToken(ResultSet rs) throws SQLException {
        String authToken = rs.getString("authToken");
        return authToken;
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authorization (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(50) NOT NULL,
              PRIMARY KEY (`authToken`),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
