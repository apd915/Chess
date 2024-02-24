package handler;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.UserData;
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
        System.out.println(request.body());

        Gson gson = new Gson();
        UserData userData = gson.fromJson(request.body(), UserData.class);

        System.out.println("this alejandor");
        System.out.println(userData);
        System.out.println("this alejandor");

        RegistrationService registration = new RegistrationService();

        String registerUser = registration.register(userData, userDAO, authDAO);


//        String jsonString = gson.toJson(registerUser);

//        return jsonString;
        return userData;

    }

}
