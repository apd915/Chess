package handler;

import Services.LogoutService;
import Response.LogoutResponse;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import spark.Request;
import Response.ErrorResponse;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {

    private UserDAO userDAO;
    private AuthDAO authDAO;

    public LogoutHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        LogoutService service = new LogoutService();
        Object logoutResponse = service.logout(authToken, userDAO, authDAO);

        if (logoutResponse == null) {
            response.type("application/json");
            response.status(200);
            return "{}";
        } else if (logoutResponse instanceof ErrorResponse) {
            if (((ErrorResponse) logoutResponse).message().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(logoutResponse);
            }
        }

        response.type("application/json");
        response.status(500);
        return gson.toJson(logoutResponse);
    }
}

