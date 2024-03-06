package dataAccessTests;

import ResponseException.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.SQLDAO.SQLAuthDAO;
import dataAccess.SQLDAO.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

class SQLUserDAOTest {

    @BeforeEach
    public void initiateDB() throws ResponseException {
        try {
            final String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(50) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(100) NOT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            };
            DatabaseManager.createDatabase();
            DatabaseManager.getConnection();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }

    @AfterEach
    public void clearTables() throws ResponseException {
        SQLUserDAO authDAO = new SQLUserDAO();
        authDAO.deleteUsers();
    }

    @Test
    public void successfulRun() throws ResponseException {
        SQLUserDAO userDAO = new SQLUserDAO();
        UserData user = new UserData("apd915", "badboni", "apd@hotmail.com");
        UserData user2 = new UserData("kili", "jeibalbim", "kili@hotmail.com");
        UserData user3 = new UserData("sebacho", "jhayco", "sebacho@hotmail.com");
        userDAO.createUser(user);
        userDAO.createUser(user2);
        userDAO.createUser(user3);
        UserData retrievedUser = userDAO.getUser(user.username());
        Assertions.assertEquals(user.username(), retrievedUser.username());
    }

    @Test
    public void failedRun() throws ResponseException {
        SQLUserDAO userDAO = new SQLUserDAO();
        UserData retrievedUser = userDAO.getUser("bad boni");
        Assertions.assertNull(retrievedUser);
    }
}

