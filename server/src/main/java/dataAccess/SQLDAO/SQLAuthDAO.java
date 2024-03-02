package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;

import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() throws ResponseException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.getConnection();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
    @Override
    public void createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        UpdateTable table = new UpdateTable();
        var statement = "INSERT INTO authorization (authToken, username) VALUES (?, ?)";
        try {
            table.executeUpdate(statement, authData.authToken(), authData.username());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public String getToken(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void deleteAuths() {

    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authorization (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(50) NOT NULL,
              PRIMARY KEY (`authToken`)
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
