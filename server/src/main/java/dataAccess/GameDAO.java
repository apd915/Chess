package dataAccess;

import model.GameData;
import gameModels.ListGamesInfo;

import java.util.HashSet;

public interface GameDAO {
    void createGame(String gameName);

    GameData getGame(int gameID);

    GameData updateGame(GameData game, String username, String clientColor);

    HashSet<ListGamesInfo> getGames();
    // will probaly call getGame iteratively

    int getGameID(String gameName);

    void deleteGames();
    // will probably call getGames

}
