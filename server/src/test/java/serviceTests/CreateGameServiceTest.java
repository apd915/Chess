package serviceTests;

import Services.ClearService;
import Services.CreateGameService;
import Services.LoginService;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import gameModels.GameName;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {

    MemoryUserDAO user = new MemoryUserDAO();
    MemoryAuthDAO auth = new MemoryAuthDAO();
    MemoryGameDAO game = new MemoryGameDAO();

    @BeforeEach
    public void createUser() {
        user.createUser(new UserData("apd915", "badboni", "apd@hotmail.com"));
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
        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        gameService.createGame(token, new GameName("New Game"), auth, game);
        Assertions.assertTrue(game.getGames().size() == 1);
    }

    @Test
    public void failedRun() {
        CreateGameService gameService = new CreateGameService();
        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        String token = auth.getToken(returningUser.username());
        gameService.createGame(token, new GameName("New Game"), auth, game);
        Assertions.assertTrue(game.getGames().size() != 1);

    }

}