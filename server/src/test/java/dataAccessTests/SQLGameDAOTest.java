package dataAccessTests;

import ResponseException.ResponseException;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import dataAccess.SQLDAO.SQLAuthDAO;
import dataAccess.SQLDAO.SQLGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {

    @BeforeEach
    public void initiateDB() throws ResponseException {
        try {
            final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(50) NULL,
              `blackUsername` varchar(50) NULL,
              `gameName` varchar(50) NOT NULL,
              `game` text NOT NULL,
              PRIMARY KEY (`gameID`)
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
        AuthDAO authDAO = new SQLAuthDAO();
        GameDAO gameDAO = new SQLGameDAO();
        AuthData authData = authDAO.getAuth("abb68800-7599-4bdb-84ab-81df3e7f0ec1");
        gameDAO.createGame("Primer Juego");
        GameData gameData = gameDAO.getGame(1);
        gameDAO.updateGame(gameData, "kili", "BLACK");
        gameDAO.updateGame(gameData, "alejo", "WHITE");
        gameDAO.deleteGames();


    }

}