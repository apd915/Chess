package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDAO;
import model.UserData;

public class SQLUserDAO  implements UserDAO {

    public SQLUserDAO() throws ResponseException{
        try {
            DatabaseManager.getConnection();
            DatabaseManager.createDatabase();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
    @Override
    public void createUser(UserData userData) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void deleteUsers() {

    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(50) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(100) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
