package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {

    ServerMessageType messageType = ServerMessageType.NOTIFICATION;
    String message;

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);

        this.message = message;
    }
}
