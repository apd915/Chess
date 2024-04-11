package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {

    String message;

    public Notification(ServerMessageType messageType, String message) {
        super(messageType);

        this.message = message;
    }
}
