package handler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
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
        return response;
    }
}
