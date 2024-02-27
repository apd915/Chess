package Response;

import gameModels.ListGamesInfo;
import gameModels.ListGamesModel;

import java.util.HashSet;

public class ListGamesResponse {

    int responseType;
    HashSet<ListGamesInfo> games;

    public ListGamesResponse(int responseType, HashSet<ListGamesInfo> games) {
        this.responseType = responseType;
        this.games = games;
    }
    public Object getResponse() {
        switch (responseType) {
            case 200:
                ListGamesModel gameList = new ListGamesModel(games);
                return gameList;
            case 401:
                return new ErrorResponse("Error: unauthorized");
            default:
                return new ErrorResponse("Error: unexpected error");
        }

    }
}
