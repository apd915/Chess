package handler;

import Models.UserData;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println(request.body());
//        var serializer = new Gson();
//        UserData user = new UserData();
//        var json = serializer.toJson(user);
//        user = serializer.fromJson(json, UserData.class);

        Gson gson = new Gson();
        UserData userData = gson.fromJson(request.body(), UserData.class);
        System.out.println("this alejandor");
        System.out.println(userData);
        System.out.println("this alejandor");

        return userData;
    }

}
