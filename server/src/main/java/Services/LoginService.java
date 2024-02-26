package Services;

import Response.LoginResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.UserData;

import java.util.Objects;

public class LoginService {

    public Object login(UserData userData, UserDAO userDAO, AuthDAO authDAO) {
        UserData user = userDAO.getUser(userData.username());
        if (user == null || (!Objects.equals(user.password(), userData.password()))) {
            LoginResponse response = new LoginResponse(401, null, null);
            return response.getResponse();
        }
        authDAO.createAuth(user.username());

        LoginResponse response = new LoginResponse(200, authDAO, user.username());
        return response.getResponse();
    }
}
