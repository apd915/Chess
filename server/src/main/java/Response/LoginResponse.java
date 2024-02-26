package Response;

import dataAccess.AuthDAO;
import model.AuthData;

public class LoginResponse {

    private int responseType;
    private AuthDAO authDAO;
    private String username;
    public LoginResponse(int responseType, AuthDAO authDAO, String username) {
        this.responseType = responseType;
        this.authDAO = authDAO;
        this.username = username;
    }

    public Object getResponse() {
        switch (responseType) {
            case 200:
                String authToken = authDAO.getToken(username);
                AuthData authData = authDAO.getAuth(authToken);
                return authData;
            case 401:
                return new ErrorResponse("Error: unauthorized");
            default:
                return new ErrorResponse("Error: unexpected error");
        }

    }
}
