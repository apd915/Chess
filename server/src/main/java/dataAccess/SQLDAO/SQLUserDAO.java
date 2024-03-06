package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import Services.LoginService;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import ResponseException.ResponseException;
import dataAccess.DatabaseManager;
import dataAccess.UserDAO;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLUserDAO  implements UserDAO {

    public SQLUserDAO() throws ResponseException{
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.getConnection();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
    @Override
    public void createUser(UserData userData) {
        // Still have to hash passwords.
        LoginService service = new LoginService();
        UpdateTable table = new UpdateTable();
//        String hashedPassword = service.storeUserPassword(userData.password());
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try {
            table.executeUpdate(statement, userData.username(), userData.password(), userData.email());
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUser(String username) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
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
    public void deleteUsers() {
        UpdateTable table = new UpdateTable();
        var statement = "TRUNCATE users";
        try {
            table.executeUpdate(statement);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(50) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(100) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private UserData readUser(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(username, password, email);
    }


}
