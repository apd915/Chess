package dataAccess;

import model.GameData;
import chess.ChessGame;
import model.ListGamesInfo;

import java.util.Random;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashSet<ListGamesInfo> gamesInfo = new HashSet<>();
    Random random = new Random();
    @Override
    public void createGame(String gameName) {
        int gameID = random.nextInt(9999);
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        ListGamesInfo gameInfo = new ListGamesInfo(gameID, null, null, gameName);
        games.put(gameID, game);
        gamesInfo.add(gameInfo);
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public GameData updateGame(String username, String ClientColor) {
        return null;
    }

    @Override
    public HashSet<ListGamesInfo> getGames() {
        return gamesInfo;
    }

    @Override
    public void deleteGames() {
        games.clear();
    }
}
