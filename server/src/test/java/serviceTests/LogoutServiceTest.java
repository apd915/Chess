package serviceTests;

import Services.ClearService;
import Services.LoginService;
import Services.LogoutService;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {

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
        LogoutService logoutService = new LogoutService();
        String token = auth.getToken(returningUser.username());
        logoutService.logout(token, user, auth);
        assertNull(auth.getAuth(token));
    }

    @Test
    public void failedRun() {
        LogoutService logoutService = new LogoutService();
        UserData returningUser = new UserData("apd915", "badboni", "apd@hotmail.com");
        String token = auth.getToken(returningUser.username());
        logoutService.logout(token, user, auth);
        assertNull(auth.getAuth(token));
    }

}