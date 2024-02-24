package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> auths = new HashMap<>();

    @Override
    public void createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        auths.put(authToken, authData);
    }

    @Override
    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }

    @Override
    public void deleteAuths() {
        auths.clear();
    }
}
