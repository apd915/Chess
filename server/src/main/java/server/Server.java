package server;

import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import handler.*;
import spark.*;

public class Server {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegistrationHandler(userDAO, authDAO));
//        Spark.post("/session", new LoginHandler());
//        Spark.delete("/session", new LogoutHandler());
//        Spark.get("/game", new ListGamesHandler());
//        Spark.post("/game", new CreateGameHandler());
//        Spark.put("/game", new JoinGameHandler());
        Spark.delete("/db", new ClearHandler());

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
