package dataAccess;

import Models.AuthData;

public class MemoryAuthDAO implements AuthDAO{
    @Override
    public void createAuth(String username) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void deleteAuths() {

    }
}