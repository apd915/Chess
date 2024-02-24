package Services;

import Response.RegistrationResponse;
import dataAccess.AuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.UserData;

public class RegistrationService {

    public String register(UserData user, UserDAO userDAO, AuthDAO authDAO) {
        if ((user.username() == null) || (user.password() == null) || (user.email() == null)) {
            RegistrationResponse response = new RegistrationResponse(400);
            return response.getResponse();
        }

        UserData username = userDAO.getUser(user.username());
        if (username != null) {
            RegistrationResponse response = new RegistrationResponse(403);
            return response.getResponse();
        }

        userDAO.createUser(user);
        authDAO.createAuth(user.username());
        RegistrationResponse response = new RegistrationResponse(200);
        return response.getResponse();
    }

}
