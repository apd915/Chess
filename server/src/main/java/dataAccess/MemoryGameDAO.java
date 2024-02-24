package dataAccess;

import model.GameData;
import chess.ChessGame;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    @Override
    public ChessGame createGame(ChessGame game) {
        return null;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public GameData updateGame(String username, String ClientColor) {
        return null;
    }

    @Override
    public HashSet<GameData> getGames() {
        return null;
    }

    @Override
    public void deleteGames() {

    }
}
