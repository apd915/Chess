package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {

    final int gameID;

    public Leave(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);

        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
