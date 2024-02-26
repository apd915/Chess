package handler;

import Services.ClearService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import Response.ClearResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {

    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    public ClearHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    @Override
    public Object handle(Request request, Response response) {
        Gson gson = new Gson();

        ClearService clear = new ClearService();
        Object clearResponse = clear.clear(userDAO, authDAO, gameDAO);

        if (clearResponse == null) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(clearResponse);
        }

        response.type("application/json");
        response.status(500);
        return gson.toJson(clearResponse);
    }

}
