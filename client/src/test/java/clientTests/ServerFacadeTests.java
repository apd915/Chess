package clientTests;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void successfulRegister() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedRegister() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successfulLogin() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedLogin() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successfulLogout() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedLogout() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successfulList() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedList() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successfulCreate() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedCreate() {
        Assertions.assertTrue(true);
    }

    @Test
    public void successfulJoin() {
        Assertions.assertTrue(true);
    }

    @Test
    public void failedJoin() {
        Assertions.assertTrue(true);
    }


}
