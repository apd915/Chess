package dataAccess;

import Models.UserData;

public class MemoryUserDAO implements UserDAO {
    // stores it in data structures

    @Override
    public void createUser(String username, String password) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void deleteUsers() {

    }
}
