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
        AuthData data = server.registerUser(new UserData("1234", "123", "123"));
        Assertions.assertNotNull(data.authToken());
    }

    @Test
    public void failedRegister() {
        ServerFacade server = new ServerFacade(url);
        // already created user
        Assertions.assertThrows(ResponseException.class, () -> server.registerUser(new UserData("apd915", "123", "123")));
    }

    @Test
    public void successfulLogin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        AuthData data = server.login(new UserData("me", "me", "123"));
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
    public void failedLogout() {
        ServerFacade server = new ServerFacade(url);
        // no login
        Assertions.assertThrows(ResponseException.class, server::logout);
    }

    @Test
    public void successfulList() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        Object games = server.listGames();
        Assertions.assertNotNull(games);
    }

    @Test
    public void failedList() {
        ServerFacade server = new ServerFacade(url);
        // no login
        Assertions.assertThrows(ResponseException.class, server::listGames);
    }

    @Test
    public void successfulCreate() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        GameID game = server.createGame(new GameName("NewGame"));
        Assertions.assertInstanceOf(GameID.class, game);
    }

    @Test
    public void failedCreate() {
        ServerFacade server = new ServerFacade(url);
        // no login
        Assertions.assertThrows(ResponseException.class, () -> server.createGame(new GameName("gaem")));
    }

    @Test
    public void successfulJoin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        GameID game = server.createGame(new GameName("joinGame3"));
        server.joinGame(new JoinGame("WHITE", game.gameID()));
        Assertions.assertTrue(true);
    }

    @Test
    public void failedJoin() throws ResponseException {
        ServerFacade server = new ServerFacade(url);
        server.login(new UserData("me", "me", "123"));
        Assertions.assertThrows(ResponseException.class, () -> server.joinGame(new JoinGame("WHITE", 1)));
    }


}
