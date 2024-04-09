package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    final CommandType commandType = CommandType.MAKE_MOVE;
    final String authToken;
    final int gameID;
    final ChessMove move;

    public MakeMove(String authToken, int gameID, ChessMove move) {
        super(authToken);

        this.authToken = authToken;
        this.gameID = gameID;
        this.move = move;
    }
}
