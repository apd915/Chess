package Services;

import Response.JoinGameResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import gameModels.JoinGame;
import model.AuthData;
import model.GameData;

public class JoinGameService {
    public Object joinGame(JoinGame joinRequest, String authToken, AuthDAO authDAO, GameDAO gameDAO) {

        AuthData authorization = authDAO.getAuth(authToken);

        if ((joinRequest.gameID() <= 0)) {
            JoinGameResponse response = new JoinGameResponse(400);
            return response.getResponse();
        }
        if (authorization == null) {
            JoinGameResponse response = new JoinGameResponse(401);
            return response.getResponse();
        }

        GameData game = gameDAO.getGame(joinRequest.gameID());

        if (game == null) {
            JoinGameResponse response = new JoinGameResponse(401);
            return response.getResponse();
        }

        if (joinRequest.playerColor() == null) {
            gameDAO.updateGame(game, authorization.username(), null);
            JoinGameResponse response = new JoinGameResponse(200);
            return response.getResponse();
        }

        if ((joinRequest.playerColor().equals("WHITE")) && (game.whiteUsername() != null)) {
            JoinGameResponse response = new JoinGameResponse(403);
            return response.getResponse();
        }
        if ((joinRequest.playerColor().equals("BLACK")) && (game.blackUsername() != null)) {
            JoinGameResponse response = new JoinGameResponse(403);
            return response.getResponse();
        }

                // make it so I can difference how to update the game based on the client's request
        gameDAO.updateGame(game, authorization.username(), joinRequest.playerColor());
        JoinGameResponse response = new JoinGameResponse(200);
        return response.getResponse();

    }
}
