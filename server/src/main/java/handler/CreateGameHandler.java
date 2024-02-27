package handler;

import Services.CreateGameService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import Response.ErrorResponse;
import gameModels.GameID;
import gameModels.GameName;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {

    AuthDAO authDAO;
    GameDAO gameDAO;

    public CreateGameHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) {

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");
        GameName gameName = gson.fromJson(request.body(), GameName.class);

        CreateGameService createGameService = new CreateGameService();
        Object createGameResponse = createGameService.createGame(authToken, gameName, authDAO, gameDAO);

        if (createGameResponse instanceof GameID) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(createGameResponse);
        }
        else if (createGameResponse instanceof ErrorResponse) {
            switch (((ErrorResponse) createGameResponse).message()) {
                case "Error: bad request":
                    response.status(400);
                    return gson.toJson(createGameResponse);
                case "Error: unauthorized":
                    response.type("application/json");
                    response.status(401);
                    return gson.toJson(createGameResponse);
            }
        }
        response.type("application/json");
        response.status(500 );
        return gson.toJson(createGameResponse);
    }

}
