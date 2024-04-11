package server;

import ResponseException.ResponseException;
import com.google.gson.Gson;
import gameModels.*;
import model.AuthData;
import model.UserData;
import SessionMessages.ServerMessageObserver;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    ServerMessageObserver serverMessageObserver;
    // URL

    public ServerFacade(String url, ServerMessageObserver handler) {
        serverUrl = url;
        serverMessageObserver = handler;
    }
    public ServerFacade() {
        this.serverUrl = "http://localhost:8080";
    }

    static String authToken = null;


    public AuthData registerUser(UserData userData) throws ResponseException {
        var path = "/user";
        AuthData registerResult = this.makeRequest("POST", path, userData, AuthData.class, authToken);
        String token = registerResult.authToken();
        authToken = token;
        return registerResult;
    }

    public AuthData login(UserData userData) throws ResponseException{
        var path = "/session";
        AuthData loginResult = this.makeRequest("POST", path, userData, AuthData.class, authToken);
        String token = loginResult.authToken();
        authToken = token;
        return loginResult;
    }

    public Object logout() throws ResponseException {
        var path = "/session";
        return this.makeRequest("DELETE", path, null, null, authToken);
    }

    public ListGamesModel listGames() throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesModel.class, authToken);
    }

    public GameID createGame(GameName gameName) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, gameName, GameID.class, authToken);
    }

    public Object joinGame(JoinGame player) throws ResponseException {
        var path = "/game";
        return this.makeRequest("PUT", path, player, null, authToken);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http, authToken);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            if (ex instanceof ResponseException) {
                throw new ResponseException(((ResponseException) ex).StatusCode(), ex.getMessage());
            }
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http, String authToken) throws IOException {
        http.addRequestProperty("authorization", authToken);
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    /*
    * WebSocket Facade
    *
    * */




    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    public String getToken() {
        return authToken;
    }
}