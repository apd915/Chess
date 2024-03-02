package serviceTests;

import Services.ClearService;
import Services.RegistrationService;
import dataAccess.MemoryDAO.MemoryAuthDAO;
import dataAccess.MemoryDAO.MemoryGameDAO;
import dataAccess.MemoryDAO.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
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
        RegistrationService service = new RegistrationService();
        UserData newUser = new UserData("Kili", "jeibalbim", "kili@yahoo.net");
        service.register(newUser, user, auth);
        Assertions.assertEquals(user.getUser("Kili"), newUser);
    }

    @Test
    public void failedRun() {
        RegistrationService service = new RegistrationService();
        UserData newUser = new UserData("apd915", "verysafepassword", "luchop@yahoo.net");
        service.register(newUser, user, auth);
        Assertions.assertNotEquals(user.getUser("apd915"), newUser);
    }

}