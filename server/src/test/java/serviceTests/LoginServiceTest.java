package serviceTests;

import Services.ClearService;
import Services.LoginService;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginServiceTest {

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
        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        Assertions.assertTrue(token != null);
    }

    @Test
    public void failedRun() {
        LoginService service = new LoginService();
        UserData returningUser = new UserData("Kili", "badboni", "kili@hotmail.com");
        service.login(returningUser, user, auth);
        String token = auth.getToken(returningUser.username());
        Assertions.assertTrue(token == null);
    }

}