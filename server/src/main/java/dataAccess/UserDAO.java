package dataAccess;

import ResponseException.ResponseException;
import model.UserData;

public interface UserDAO {
    void createUser(UserData userData);

    UserData getUser(String username);

    void deleteUsers();

}
