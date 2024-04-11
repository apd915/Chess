package webSocketMessages.serverMessages;

public class LoadGame extends ServerMessage {

    int game;

    public LoadGame(ServerMessageType messageType, int game) {
        super(messageType);

        this.game = game;
    }
}
