package Response;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class ClearResponse {

    int responseType;
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;

    public ClearResponse(int responseType, UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.responseType = responseType;
    }

    public Object getResponse() {
        switch (responseType) {
            case 200:
                return null;
            default:
                return new ErrorResponse("Error: unexpected error");

        }
    }

}
