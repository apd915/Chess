package Response;

import dataAccess.AuthDAO;
import model.AuthData;

public class RegistrationResponse {
    private int responseType;
    private AuthDAO authDAO;
    private String username;
    public RegistrationResponse(int responseType, AuthDAO authDAO, String username) {
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
            case 400:
                return new ErrorResponse("Error: bad request");
            case 403:
                return new ErrorResponse("Error: username taken");
            default:
                return new ErrorResponse("Error: unexpected error");
        }

    }

}
