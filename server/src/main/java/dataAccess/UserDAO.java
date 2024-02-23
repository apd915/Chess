package dataAccess;

import Models.UserData;

public interface UserDAO {
    void createUser(String username, String password);

    UserData getUser(String username);

    void deleteUsers();

}
