package handler;

import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        System.out.println(request.body());
        // transform request.body() into userData object.
        return "hello";
    }

}
