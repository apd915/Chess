package Services;

import Response.LoginResponse;
import Response.LogoutResponse;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;

public class LogoutService {
    public Object logout(String authToken, UserDAO userDAO, AuthDAO authDAO) {
        AuthData authorization = authDAO.getAuth(authToken);
        if (authorization == null) {
            LogoutResponse response = new LogoutResponse(401);
            return response.getResponse();
        }

        authDAO.deleteAuth(authorization.authToken());
        LogoutResponse response = new LogoutResponse(200);
        return response.getResponse();
    }
}
