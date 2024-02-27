package dataAccess;

import model.GameData;
import chess.ChessGame;
import gameModels.ListGamesInfo;

import java.util.Random;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashSet<ListGamesInfo> gamesInfo = new HashSet<>();
    final private HashMap<String, Integer> ID = new HashMap<>();
    Random random = new Random();
    @Override
    public void createGame(String gameName) {
        int gameID = random.nextInt(9999);
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        ListGamesInfo gameInfo = new ListGamesInfo(gameID, null, null, gameName);
        games.put(gameID, game);
        gamesInfo.add(gameInfo);
        ID.put(gameName, gameID);
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
    public int getGameID(String gameName) {
        return ID.get(gameName);
    }
    @Override
    public void deleteGames() {
        games.clear();
        gamesInfo.clear();
        ID.clear();
    }
}
