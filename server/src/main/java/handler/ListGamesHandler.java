package handler;

import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return response;
    }
}
