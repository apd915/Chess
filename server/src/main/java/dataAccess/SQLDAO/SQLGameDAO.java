package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import gameModels.ListGamesInfo;
import model.AuthData;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO() throws ResponseException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.getConnection();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
    @Override
    public void createGame(String gameName) {
        UpdateTable table = new UpdateTable();
        Gson gson = new Gson();
        ChessGame game = new ChessGame();
        String gameJson = gson.toJson(game);
        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
        try {
            var id = table.executeUpdate(statement, null, null, gameName, gameJson);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GameData getGame(int gameID) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
//            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
            return null;
        }
        return null;    }

    @Override
    public GameData updateGame(GameData game, String username, String clientColor) {
        if (Objects.equals(clientColor, "WHITE")) {
            UpdateTable table = new UpdateTable();
            var statement = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
            try {
                table.executeUpdate(statement, username, game.gameID());
                return new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.equals(clientColor, "BLACK")) {
            UpdateTable table = new UpdateTable();
            var statement = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
            try {
                table.executeUpdate(statement, username, game.gameID());
                return new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }
        }
        return game;
    }

    @Override
    public HashSet<ListGamesInfo> getGames()  {
        var result = new HashSet<ListGamesInfo>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGameInfo(rs));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    @Override
    public int getGameID(String gameName) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID FROM game WHERE gameName=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readID(rs);
                    }
                }
            }
        } catch (Exception e) {
//            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
            return 0;
        }
        return 0;
    }

    @Override
    public void deleteGames() {
        UpdateTable table = new UpdateTable();
        var statement = "TRUNCATE game";
        try {
            table.executeUpdate(statement);
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        Gson gson = new Gson();
        String whiteUsername;
        String blackUsername;
        String gameName;
        int gameID = rs.getInt("gameID");
        try {
            whiteUsername = rs.getString("whiteUsername");
        }
        catch (SQLException e) {
            whiteUsername = null;
        }
        try {
            blackUsername = rs.getString("blackUsername");
        }
        catch (SQLException e) {
            blackUsername = null;
        }
        try {
            gameName = rs.getString("gameName");        }
        catch (SQLException e) {
            gameName = null;
        }
        String game = rs.getString("game");
        ChessGame chessGame = gson.fromJson(game, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    private ListGamesInfo readGameInfo(ResultSet rs) throws SQLException {
        String whiteUsername;
        String blackUsername;
        String gameName;
        int gameID = rs.getInt("gameID");
        try {
            whiteUsername = rs.getString("whiteUsername");
        }
        catch (SQLException e) {
            whiteUsername = null;
        }
        try {
            blackUsername = rs.getString("blackUsername");
        }
        catch (SQLException e) {
            blackUsername = null;
        }
        try {
            gameName = rs.getString("gameName");        }
        catch (SQLException e) {
            gameName = null;
        }
        return new ListGamesInfo(gameID, whiteUsername, blackUsername, gameName);
    }

    private int readID(ResultSet rs) throws SQLException {
        return rs.getInt("gameID");
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(50) NULL,
              `blackUsername` varchar(50) NULL,
              `gameName` varchar(50) NOT NULL,
              `game` text NOT NULL,
              PRIMARY KEY (`gameID`),
              INDEX (gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
