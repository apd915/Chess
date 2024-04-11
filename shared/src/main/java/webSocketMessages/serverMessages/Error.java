package webSocketMessages.serverMessages;

public class Error extends ServerMessage{

    String errorMessage;

    public Error(ServerMessageType messageType, String errorMessage) {
        super(messageType);

        this.errorMessage = errorMessage;
    }
}
