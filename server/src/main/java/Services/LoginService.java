package Services;

import Response.LoginResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class LoginService {

    public Object login(UserData userData, UserDAO userDAO, AuthDAO authDAO) {
        UserData user = userDAO.getUser(userData.username());
//        String hashedPassword = storeUserPassword(userData.password());
        if (user == null || (!Objects.equals(user.password(), userData.password()))) {
            LoginResponse response = new LoginResponse(401, null, null);
            return response.getResponse();
        }
        // createAuth to return authdata
        AuthData authData = authDAO.createAuth(user.username());

//        LoginResponse response = new LoginResponse(200, authDAO, user.username());
        return authData;
    }

    public String storeUserPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
