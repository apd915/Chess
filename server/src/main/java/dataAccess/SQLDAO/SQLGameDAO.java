package dataAccess.SQLDAO;

import dataAccess.GameDAO;
import gameModels.ListGamesInfo;
import model.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO {
    @Override
    public void createGame(String gameName) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public GameData updateGame(GameData game, String username, String clientColor) {
        return null;
    }

    @Override
    public HashSet<ListGamesInfo> getGames() {
        return null;
    }

    @Override
    public int getGameID(String gameName) {
        return 0;
    }

    @Override
    public void deleteGames() {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(50) NULL,
              `blackUsername` varchar(50) NULL,
              'gameName' varchar(50) NOT NULL,
              'game' text NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
