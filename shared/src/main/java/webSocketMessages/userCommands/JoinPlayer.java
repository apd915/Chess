package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {

    final int gameID;
    ChessGame.TeamColor playerColor;

    public JoinPlayer(String authToken, CommandType commandType, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken, commandType);

        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
