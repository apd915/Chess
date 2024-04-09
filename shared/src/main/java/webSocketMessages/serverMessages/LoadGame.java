package webSocketMessages.serverMessages;

public class LoadGame extends ServerMessage {
    ServerMessageType messageType = ServerMessageType.LOAD_GAME;
    int game;

    public LoadGame(int game) {
        super(ServerMessageType.LOAD_GAME);

        this.game = game;
    }
}
