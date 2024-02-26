package handler;

import Services.LoginService;
import Services.RegistrationService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import Response.ErrorResponse;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {

    private UserDAO userDAO;
    private AuthDAO authDAO;

    public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        UserData userData = gson.fromJson(request.body(), UserData.class);

        LoginService loginService = new LoginService();
        Object loginResponse = loginService.login(userData, userDAO, authDAO);


        if (loginResponse instanceof AuthData) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(loginResponse);
        }
        else if (loginResponse instanceof ErrorResponse) {
            if (((ErrorResponse) loginResponse).message().equals("Error: unauthorized")) {
                response.status(401);
                return gson.toJson(loginResponse);
            }
        }

        response.type("application/json");
        response.status(500);
        return gson.toJson(loginResponse);

    }
}
