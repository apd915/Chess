package Response;

import gameModels.GameID;

public class CreateGameResponse {

    int responseType;
    int gameID;
    public CreateGameResponse(int responseType, Integer gameID) {
        this.responseType = responseType;
        this.gameID = gameID;
    }


    public Object getResponse() {
        switch (responseType) {
            case 200:
                GameID gameList = new GameID(gameID);
                return gameList;
            case 400:
                return new ErrorResponse("Error: bad request");
            case 401:
                return new ErrorResponse("Error: unauthorized");
            default:
                return new ErrorResponse("Error: unexpected error");
        }
    }
}
