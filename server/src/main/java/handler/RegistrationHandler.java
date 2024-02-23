package handler;

import Models.UserData;
import Services.RegistrationService;
import Response.RegistrationResponse;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println(request.body());

        Gson gson = new Gson();
        UserData userData = gson.fromJson(request.body(), UserData.class);

        System.out.println("this alejandor");
        System.out.println(userData);
        System.out.println("this alejandor");

        RegistrationService registration = new RegistrationService();
//        RegistrationResponse registerUser = registration.register(userData);

//        String jsonString = gson.toJson(registerUser);

//        return jsonString;
        return userData;

    }

}
