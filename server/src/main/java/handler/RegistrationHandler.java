package handler;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import Response.ErrorResponse;
import Services.RegistrationService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationHandler implements Route {

    private UserDAO userDAO;
    private AuthDAO authDAO;

    public RegistrationHandler(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();
        UserData userData = gson.fromJson(request.body(), UserData.class);


        RegistrationService registration = new RegistrationService();
        Object registerUser = registration.register(userData, userDAO, authDAO);

        if (registerUser instanceof AuthData) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(registerUser);
        }
        else if (registerUser instanceof ErrorResponse) {
            switch (((ErrorResponse) registerUser).message()) {
                case "Error: bad request":
                    response.status(400);
                    return gson.toJson(registerUser);
                case "Error: username taken":
                    response.type("application/json");
                    response.status(403);
                    return gson.toJson(registerUser);
            }
        }
        response.type("application/json");
        response.status(500 );
        return gson.toJson(registerUser);
    }

}
