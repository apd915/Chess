package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(String username);

    AuthData getAuth(String authToken);

    String getToken(String username);

    void deleteAuth(String authToken);

    void deleteAuths();

}
