package dataAccess;

import model.GameData;
import chess.ChessGame;
import model.ListGamesInfo;

import java.util.HashSet;

public interface GameDAO {
    void createGame(String gameName);

    GameData getGame(int gameID);

    GameData updateGame(String username, String ClientColor);

    HashSet<ListGamesInfo> getGames();
    // will probaly call getGame iteratively

    void deleteGames();
    // will probably call getGames

}
