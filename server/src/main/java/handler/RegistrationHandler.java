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
//        int status = registration.
        // gson takes in an object, turns it into json. the object should have as a parameter the left side of the response
        // For this one it will be an authData object that will be passed in as Json
//        System.out.println(gson.toJson("\"username\":\"\", \"authToken\":\"\" "));

        if (registerUser instanceof AuthData) {
            response.type("application/json");
            response.status(200);
            return gson.toJson(registerUser);
        }
        else if (registerUser instanceof ErrorResponse) {
            switch (((ErrorResponse) registerUser).message()) {
                case "\"Error: bad request\"":
                    response.status(400);
                    return gson.toJson(registerUser);
                case "\"Error: username taken\"":
                    response.type("application/json");
                response.status(403);
                return gson.toJson(registerUser);
        }
//        switch (registerUser) {
//            case "\"message\": \"Error: bad request\"":
//                response.type("application/json");
//                response.status(400 );
//                return gson.toJson(registerUser);
//
//            case "\"message\": \"Error: username taken\"":
//                response.type("application/json");
//                response.status(403 );
//                return gson.toJson(registerUser);
//
//            case "\"message\": \"Error: unexpected error\"":
//                response.type("application/json");
//                response.status(500 );
//                return gson.toJson(registerUser);
//
//            default:
//                response.type("application/json");
//                response.status(200);
//                return gson.toJson("\"username\":\"\", \"authToken\":\"\" ");



        }
        response.type("application/json");
        response.status(500 );
        return gson.toJson(registerUser);
    }

}
