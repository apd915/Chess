package Services;

import Response.CreateGameResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import model.AuthData;
import gameModels.GameName;

public class CreateGameService {
    public Object createGame(String authToken, GameName gameName, AuthDAO authDAO, GameDAO gameDAO) {
        AuthData authorization = authDAO.getAuth(authToken);

        if (gameName.gameName() == null) {
            CreateGameResponse response = new CreateGameResponse(400, -1);
            return response.getResponse();
        }
        if (authorization == null) {
            CreateGameResponse response = new CreateGameResponse(401, -1);
            return response.getResponse();
        }
        gameDAO.createGame(gameName.gameName());
        int gameID = gameDAO.getGameID(gameName.gameName());
        CreateGameResponse response = new CreateGameResponse(200, gameID);
        return response.getResponse();
    }
}
