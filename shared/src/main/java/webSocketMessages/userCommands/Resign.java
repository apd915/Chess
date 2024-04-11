package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    final int gameID;

    public Resign(String authToken, CommandType commandType, int gameID) {
        super(authToken, commandType);

        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

}
