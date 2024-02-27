package handler;

import Services.ListGamesService;
import Services.LogoutService;
import Response.ListGamesResponse;
import Response.ErrorResponse;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import model.ListGamesModel;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {

    AuthDAO authDAO;
    GameDAO gameDAO;

    public ListGamesHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        ListGamesService service = new ListGamesService();
        Object listGamesResponse = service.listGames(authToken, gameDAO, authDAO);

        if (listGamesResponse instanceof ListGamesModel) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(listGamesResponse);
        } else if (listGamesResponse instanceof ErrorResponse) {
            if (((ErrorResponse) listGamesResponse).message().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(listGamesResponse);
            }
        }

        response.type("application/json");
        response.status(500);
        return gson.toJson(listGamesResponse);
    }
}
