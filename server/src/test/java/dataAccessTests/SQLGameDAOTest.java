package dataAccessTests;

import ResponseException.ResponseException;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import dataAccess.SQLDAO.SQLAuthDAO;
import dataAccess.SQLDAO.SQLGameDAO;
import dataAccess.SQLDAO.SQLUserDAO;
import gameModels.ListGamesInfo;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;

import java.util.HashSet;

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

    @AfterEach
    public void clearTables() throws ResponseException {
        SQLGameDAO gameDAO = new SQLGameDAO();
        gameDAO.deleteGames();
    }

    @Test
    public void successfulRun() throws ResponseException {
        GameDAO gameDAO = new SQLGameDAO();
        gameDAO.createGame("Primer Juego");
        GameData gameData = gameDAO.getGame(1);
        gameDAO.updateGame(gameData, "kili", "BLACK");
        gameDAO.updateGame(gameData, "alejo", "WHITE");
        GameData newData = gameDAO.getGame(1);
        Assertions.assertEquals(newData.blackUsername(), "kili");
    }

    @Test
    public void failedRun() throws ResponseException {
        GameDAO gameDAO = new SQLGameDAO();
        gameDAO.createGame("Primer Juego");
        gameDAO.createGame("Segundo Juego");
        gameDAO.createGame("Tercer Juego");
        gameDAO.createGame("Cuarto Juego");
        HashSet<ListGamesInfo> games = gameDAO.getGames();
        Assertions.assertNotEquals(games.size(), 5);
    }

}