package server;

import dataAccess.*;
import dataAccess.SQLDAO.SQLAuthDAO;
import dataAccess.SQLDAO.SQLGameDAO;
import dataAccess.SQLDAO.SQLUserDAO;
import ResponseException.ResponseException;
import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        // try catch for SQL DAO's. Implement them inside run()

        try {
            UserDAO userDAO = new SQLUserDAO();
            AuthDAO authDAO = new SQLAuthDAO();
            GameDAO gameDAO = new SQLGameDAO();

            Spark.port(desiredPort);

            Spark.staticFiles.location("web");

            Spark.webSocket("/connect", WSServer.class);

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
        } catch (ResponseException e) {
            return 1;
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
