package handler;

import Services.JoinGameService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import gameModels.JoinGame;
import Response.ErrorResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {

    AuthDAO authDAO;
    GameDAO gameDAO;
    public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();
        JoinGame joinRequest = gson.fromJson(request.body(), JoinGame.class);
        String authToken = request.headers("Authorization");

        JoinGameService joinGameService = new JoinGameService();
        Object joinGameResponse = joinGameService.joinGame(joinRequest, authToken, authDAO, gameDAO);

        if (joinGameResponse == null) {
            response.type("application/json");
            response.status(200);
            return "{}";
        }
        if (joinGameResponse instanceof ErrorResponse) {
            switch (((ErrorResponse) joinGameResponse).message()) {
                case "Error: bad request":
                    response.status(400);
                    return gson.toJson(joinGameResponse);
                case "Error: unauthorized":
                    response.type("application/json");
                    response.status(401);
                    return gson.toJson(joinGameResponse);
                case "Error: already taken":
                    response.type("application/json");
                    response.status(403);
                    return gson.toJson(joinGameResponse);
            }
        }
        response.type("application/json");
        response.status(500 );
        return gson.toJson(joinGameResponse);

    }
}
