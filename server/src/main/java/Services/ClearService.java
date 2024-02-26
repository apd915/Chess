package Services;

import Response.ClearResponse;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class ClearService {
    public Object clear(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        userDAO.deleteUsers();
        authDAO.deleteAuths();
        gameDAO.deleteGames();

        ClearResponse response = new ClearResponse(200, userDAO, authDAO, gameDAO);
        return response.getResponse();

    }
}

