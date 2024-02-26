package dataAccess;

import model.AuthData;
import model.GameData;
import chess.ChessGame;
import java.util.Random;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    Random random = new Random();
    @Override
    public void createGame(String gameName) {
        int gameID = random.nextInt(9999);
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        games.put(gameID, game);
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
    public HashSet<GameData> getGames() {
        return new HashSet<>(games.values());
    }

    @Override
    public void deleteGames() {
        games.clear();
    }
}
