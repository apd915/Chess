package dataAccess;

import Models.AuthData;

public interface AuthDAO {

    void createAuth(String username);

    AuthData getAuth(String authToken);

    void deleteAuth(String authToken);

    void deleteAuths();

}
