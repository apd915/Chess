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


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authorization (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(50) NOT NULL,
              PRIMARY KEY (`authToken`)
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
}
