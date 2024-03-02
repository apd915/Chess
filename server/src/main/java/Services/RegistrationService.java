package Services;

import Response.RegistrationResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.UserData;

public class RegistrationService {

    public Object register(UserData user, UserDAO userDAO, AuthDAO authDAO) {
        if ((user.username() == null) || (user.password() == null) || (user.email() == null)) {
            RegistrationResponse response = new RegistrationResponse(400, null, null);
            return response.getResponse();
        }

        UserData username = userDAO.getUser(user.username());
        if (username != null) {
            RegistrationResponse response = new RegistrationResponse(403, null, null);
            return response.getResponse();
        }

        userDAO.createUser(user);
        authDAO.createAuth(user.username());

        RegistrationResponse response = new RegistrationResponse(200, authDAO, user.username());
        return response.getResponse();
    }

}
