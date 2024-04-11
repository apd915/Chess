package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    final int gameID;
    final ChessMove move;

    public MakeMove(String authToken, CommandType commandType, int gameID, ChessMove move) {
        super(authToken, commandType);

        this.gameID = gameID;
        this.move = move;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
