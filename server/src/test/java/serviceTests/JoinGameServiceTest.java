package serviceTests;

import Services.ClearService;
import Services.CreateGameService;
import Services.JoinGameService;
import Services.LoginService;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import gameModels.GameName;
import gameModels.JoinGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {

    MemoryUserDAO user = new MemoryUserDAO();
    MemoryAuthDAO auth = new MemoryAuthDAO();
    MemoryGameDAO game = new MemoryGameDAO();

    @BeforeEach
    public void createUser() {
        user.createUser(new UserData("apd915", "badboni", "apd@hotmail.com"));
        user.createUser(new UserData("kili", "badboni", "apd@hotmail.com"));

    }

    @AfterEach
    public void deleteUsers() {
        ClearService clear = new ClearService();
        clear.clear(user, auth, game);
    }

    @Test
    public void successfulRun() {
        LoginService service = new LoginService();
        CreateGameService gameService = new CreateGameService();
        JoinGameService joinService = new JoinGameService();

        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        gameService.createGame(token, new GameName("New Game"), auth, game);
        int gameID = game.getGameID("New Game");
        joinService.joinGame(new JoinGame("WHITE", gameID), token, auth, game);

        UserData returningUser2 = new UserData("kili", "badboni", "apd@hotmail.com");
        service.login(returningUser2, user, auth);
        String token2 = auth.getToken(returningUser2.username());
        joinService.joinGame(new JoinGame("WHITE", gameID), token2, auth, game);

        GameData gameData = game.getGame(gameID);

        Assertions.assertNotEquals(gameData.whiteUsername(), "kili");
    }

    @Test
    public void failedRun() {
        LoginService service = new LoginService();
        CreateGameService gameService = new CreateGameService();
        JoinGameService joinService = new JoinGameService();

        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        gameService.createGame(token, new GameName("New Game"), auth, game);
        int gameID = game.getGameID("New Game");
        joinService.joinGame(new JoinGame("WHITE", gameID), token, auth, game);

        UserData returningUser2 = new UserData("kili", "badboni", "apd@hotmail.com");
        service.login(returningUser2, user, auth);
        String token2 = auth.getToken(returningUser2.username());
        joinService.joinGame(new JoinGame("BLACK", gameID), token2, auth, game);

        GameData gameData = game.getGame(gameID);

        Assertions.assertEquals(gameData.blackUsername(), "kili");
    }
}