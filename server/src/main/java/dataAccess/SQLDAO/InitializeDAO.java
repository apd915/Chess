package dataAccess.SQLDAO;

import ResponseException.ResponseException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;

public class InitializeDAO {

    public InitializeDAO() {}

    public void initialize(String[] createStatements) throws ResponseException {
        try {
            DatabaseManager.createDatabase();
            DatabaseManager.getConnection();
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
}
