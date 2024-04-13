package Services;

import Response.LoginResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginService {

    public Object login(UserData userData, UserDAO userDAO, AuthDAO authDAO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserData user = userDAO.getUser(userData.username());
//        String hashedPassword = storeUserPassword(userData.password());
        if (user == null || (!encoder.matches(userData.password(), user.password()))) {
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
