package dataAccessTests;

import ResponseException.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.SQLDAO.SQLAuthDAO;
import model.AuthData;
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

    @Test
    public void successfulRun() throws ResponseException {
        SQLAuthDAO authDAO = new SQLAuthDAO();
//        authDAO.createAuth("apd915");
//        authDAO.createAuth("sebacho");
//        authDAO.createAuth("kili");
        AuthData authData = authDAO.getAuth("dd568996-3199-485a-ba17-0f668b7ab595");
//        authDAO.deleteAuth("a960465d-27bf-414b-b389-5707f9e63c6c");
        authDAO.deleteAuths();
        System.out.println(authData);
    }

}