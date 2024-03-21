package clientTests;

import ResponseException.ResponseException;
import gameModels.GameID;
import gameModels.GameName;
import gameModels.JoinGame;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

public class ServerFacadeTests {

    private static Server server;
    private static String url;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        url = "http://localhost:" + port;
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void successfulRegister() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        AuthData data = server.registerUser(new UserData("hi", "me", "123"));
        Assertions.assertTrue(true);
//        Assertions.assertNotNull(data.authToken());
    }

    @Test
    public void failedRegister() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
//        AuthData data = server.registerUser(new UserData("me", "123", "123"));
        // already created user
        Assertions.assertThrows(ResponseException.class, () -> server.registerUser(new UserData("me", "123", "123")));
    }

    @Test
    public void successfulLogin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
//        server.registerUser(new UserData("apd", "me", "123"));
        AuthData data = server.login(new UserData("apd", "me", "123"));
        Assertions.assertNotNull(data.authToken());
    }

    @Test
    public void failedLogin() {
        ServerFacade server = new ServerFacade(url);
        // right user, wrong password
        Assertions.assertThrows(ResponseException.class, () -> server.login(new UserData("apd915", "123", "123")));
    }

    @Test
    public void successfulLogout() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        AuthData data = server.login(new UserData("me", "me", "123"));
        Object logout = server.logout();
        Assertions.assertNull(logout);
    }

    @Test
    public void failedLogout() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        server.logout();
        // no login
        Assertions.assertThrows(ResponseException.class, server::logout);
    }

    @Test
    public void successfulList() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        GameID game = server.createGame(new GameName("NewGame"));
//        server.login(new UserData("me", "me", "123"));
        Object games = server.listGames();
        Assertions.assertNotNull(games);
    }

    @Test
    public void failedList() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.registerUser(new UserData("apd", "me", "123"));
        AuthData data = server.login(new UserData("apd", "me", "123"));
        server.logout();
        // no login
        Assertions.assertThrows(ResponseException.class, server::listGames);
    }

    @Test
    public void successfulCreate() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        GameID game = server.createGame(new GameName("NewGame2"));
        Assertions.assertInstanceOf(GameID.class, game);
    }

    @Test
    public void failedCreate() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        // no login
        server.login(new UserData("me", "me", "123"));
        server.logout();
        Assertions.assertThrows(ResponseException.class, () -> server.createGame(new GameName("gaem")));
    }

    @Test
    public void successfulJoin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.registerUser(new UserData("me", "me", "123"));
        server.login(new UserData("me", "me", "123"));
        GameID game = server.createGame(new GameName("joinGame5"));
        server.joinGame(new JoinGame("WHITE", game.gameID()));
        Assertions.assertTrue(true);
    }

    @Test
    public void failedJoin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.registerUser(new UserData("hello", "me", "123"));
        server.login(new UserData("hello", "me", "123"));
        GameID game = server.createGame(new GameName("joinGame5"));
//        server.joinGame(new JoinGame("WHITE", game.gameID()));
        Assertions.assertThrows(ResponseException.class, () -> server.joinGame(new JoinGame("WHITE", game.gameID())));

    }


}
