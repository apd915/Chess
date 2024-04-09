package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    private final CommandType commandType = CommandType.LEAVE;
    final String authToken;
    final int gameID;

    public Resign(String authToken, int gameID) {
        super(authToken);

        this.authToken = authToken;
        this.gameID = gameID;
    }
}
