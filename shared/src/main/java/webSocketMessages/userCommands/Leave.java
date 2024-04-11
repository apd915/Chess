package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {

    final CommandType commandType = CommandType.LEAVE;
    final String authToken;
    final int gameID;

    public Leave(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);

        this.authToken = authToken;
        this.gameID = gameID;
    }
}
