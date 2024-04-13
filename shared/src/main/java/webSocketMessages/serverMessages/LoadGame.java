package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage {

    ChessGame game;

    public LoadGame(ServerMessageType messageType, ChessGame game) {
        super(messageType);

        this.game = game;
    }

    public ChessGame getGame() {
        return game;
    }
}
