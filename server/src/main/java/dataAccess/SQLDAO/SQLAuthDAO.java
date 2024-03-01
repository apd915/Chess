package dataAccess.SQLDAO;

import dataAccess.AuthDAO;
import model.AuthData;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void createAuth(String username) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public String getToken(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void deleteAuths() {

    }
}
