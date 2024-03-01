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
}
