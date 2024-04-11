package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand {

    final int gameID;

    public JoinObserver(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);
        this.gameID =gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
