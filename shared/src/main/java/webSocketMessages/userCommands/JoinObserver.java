package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand {

    final CommandType commandType = CommandType.JOIN_OBSERVER;
    final String authToken;
    final int gameID;

    public JoinObserver(String authToken, int gameID) {
        super(authToken);

        this.authToken = authToken;
        this.gameID =gameID;
    }
}
