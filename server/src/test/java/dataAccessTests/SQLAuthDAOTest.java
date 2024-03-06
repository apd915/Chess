package dataAccessTests;

import ResponseException.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.SQLDAO.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {

    @BeforeEach
    public void initiateDB() throws ResponseException {
        try {
            final String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS  authorization (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(50) NOT NULL,
              PRIMARY KEY (`authToken`),
              INDEX(username)
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
        SQLAuthDAO authDAO = new SQLAuthDAO();
        authDAO.deleteAuths();
    }

    @Test
    public void successfulRun() throws ResponseException {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.createAuth("apd915");
        authDAO.createAuth("sebacho");
        authDAO.createAuth("kili");
        AuthData authorization = authDAO.getAuth(authData.authToken());
        Assertions.assertEquals(authData, authorization);
    }

    @Test
    public void failedRun() throws ResponseException {
        SQLAuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.createAuth("apd915");
        AuthData authData2 = authDAO.createAuth("sebacho");
        Assertions.assertNotEquals(authData.authToken(), authData2.authToken());
    }

}