package webSocketMessages.serverMessages;

public class Error extends ServerMessage{

    ServerMessageType messageType = ServerMessageType.ERROR;
    String errorMessage;

    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);

        this.errorMessage = errorMessage;
    }
}
