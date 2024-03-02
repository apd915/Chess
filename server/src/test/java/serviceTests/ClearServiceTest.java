package serviceTests;

import Services.ClearService;
import Services.CreateGameService;
import Services.LoginService;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import gameModels.GameName;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClearServiceTest {

    MemoryUserDAO user = new MemoryUserDAO();
    MemoryAuthDAO auth = new MemoryAuthDAO();
    MemoryGameDAO game = new MemoryGameDAO();

    @BeforeEach
    public void createUser() {
        user.createUser(new UserData("apd915", "badboni", "apd@hotmail.com"));
        user.createUser(new UserData("kili", "badboni", "apd@hotmail.com"));
        user.createUser(new UserData("other user", "badboni", "apd@hotmail.com"));
        LoginService service = new LoginService();
        CreateGameService gameService = new CreateGameService();
        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        gameService.createGame(token, new GameName("New Game"), auth, game);
        gameService.createGame(token, new GameName("Other Game"), auth, game);
    }

    @Test
    public void successfulRun() {
        ClearService clear = new ClearService();
        clear.clear(user, auth, game);
        boolean userSize = (user.getUser("apd915") == null);
        boolean authSize = (auth.getToken("apd915") == null);
        boolean gameSize = (game.getGames().isEmpty());
        Assertions.assertEquals(userSize, authSize, String.valueOf(gameSize));
    }

    @Test
    public void failedRun() {
        ClearService clear = new ClearService();
        clear.clear(user, auth, game);
        boolean userSize = (user.getUser("apd915") == null);
        boolean authSize = (auth.getToken("apd915") == null);
        boolean gameSize = (game.getGames().isEmpty());
        Assertions.assertNotEquals(game.getGames().size(), 1);
    }

}