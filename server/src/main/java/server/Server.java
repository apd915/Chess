package server;

import dataAccess.*;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import handler.*;
import spark.*;

public class Server {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    public int run(int desiredPort) {
        // try catch for SQL DAO's. Implement them inside run()

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegistrationHandler(userDAO, authDAO));
        Spark.post("/session", new LoginHandler(userDAO, authDAO));
        Spark.delete("/session", new LogoutHandler(userDAO, authDAO));
        Spark.get("/game", new ListGamesHandler(authDAO, gameDAO));
        Spark.post("/game", new CreateGameHandler(authDAO, gameDAO));
        Spark.put("/game", new JoinGameHandler(authDAO, gameDAO));
        Spark.delete("/db", new ClearHandler(userDAO, authDAO, gameDAO));


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
