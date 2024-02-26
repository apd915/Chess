package dataAccess;

import model.AuthData;
import model.GameData;
import chess.ChessGame;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    @Override
    public ChessGame createGame(ChessGame game) {
        return game;
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
