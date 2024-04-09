package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {

    final CommandType commandType = CommandType.JOIN_PLAYER;
    final String authToken;
    final int gameID;
    ChessGame.TeamColor playerColor;

    public JoinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);

        this.authToken = authToken;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }
}
