package dataAccess;

import model.GameData;
import chess.ChessGame;
import gameModels.ListGamesInfo;

import java.util.Objects;
import java.util.Random;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();
    final private HashSet<ListGamesInfo> gamesInfo = new HashSet<>();
    final private HashMap<String, Integer> id = new HashMap<>();
    final private HashMap<Integer, ListGamesInfo> gamesInfoMap = new HashMap<>();
    Random random = new Random();
    @Override
    public void createGame(String gameName) {
        int gameID = random.nextInt(9999);
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        ListGamesInfo gameInfo = new ListGamesInfo(gameID, null, null, gameName);
        gamesInfoMap.put(gameID, gameInfo);
        games.put(gameID, game);
        gamesInfo.add(gameInfo);
        id.put(gameName, gameID);
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public GameData updateGame(GameData game, String username, String clientColor) {
        if (Objects.equals(clientColor, "WHITE")) {
            GameData updatedGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
            games.remove(game.gameID());
            games.put(updatedGame.gameID(), updatedGame );

            ListGamesInfo gameInfo = gamesInfoMap.get(updatedGame.gameID());

            gamesInfo.remove(gameInfo);
            gamesInfo.add(new ListGamesInfo(updatedGame.gameID(), updatedGame.whiteUsername(),
                    updatedGame.blackUsername(), updatedGame.gameName()));

            gamesInfoMap.remove(updatedGame.gameID());
            gamesInfoMap.put(updatedGame.gameID(), new ListGamesInfo(updatedGame.gameID(), updatedGame.whiteUsername(),
                    updatedGame.blackUsername(), updatedGame.gameName()));
            return updatedGame;
        }
        if (Objects.equals(clientColor, "BLACK")) {
            GameData updatedGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
            games.remove(game.gameID());
            games.put(updatedGame.gameID(), updatedGame );

            ListGamesInfo gameInfo = gamesInfoMap.get(updatedGame.gameID());

            gamesInfo.remove(gameInfo);
            gamesInfo.add(new ListGamesInfo(updatedGame.gameID(), updatedGame.whiteUsername(),
                    updatedGame.blackUsername(), updatedGame.gameName()));

            gamesInfoMap.remove(updatedGame.gameID());
            gamesInfoMap.put(updatedGame.gameID(), new ListGamesInfo(updatedGame.gameID(), updatedGame.whiteUsername(),
                    updatedGame.blackUsername(), updatedGame.gameName()));
            return updatedGame;
        }
        return game;
    }

    @Override
    public HashSet<ListGamesInfo> getGames() {
        return gamesInfo;
    }

    @Override
    public int getGameID(String gameName) {
        return id.get(gameName);
    }
    @Override
    public void deleteGames() {
        games.clear();
        gamesInfo.clear();
        id.clear();
        gamesInfoMap.clear();
    }
}
