package Services;

import Response.ListGamesResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import model.AuthData;
import gameModels.ListGamesInfo;

import java.util.HashSet;

public class ListGamesService {
    public Object listGames(String authToken, GameDAO gameDAO, AuthDAO authDAO) {
        AuthData authorization = authDAO.getAuth(authToken);
        if (authorization == null) {
            ListGamesResponse response = new ListGamesResponse(401, null);
            return response.getResponse();
        }

        HashSet<ListGamesInfo> games = gameDAO.getGames();
        ListGamesResponse response = new ListGamesResponse(200, games);
        return response.getResponse();

    }
}
