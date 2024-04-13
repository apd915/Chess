package server;

import ResponseException.ResponseException;
import chess.*;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.SQLDAO.SQLAuthDAO;
import dataAccess.SQLDAO.SQLGameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import server.WSHelper.WSMoveHelper;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@WebSocket
public class WSServer {

    private static HashMap<Integer, List<Session>> sessions = new HashMap<>();


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        Gson gson = new Gson();
        UserGameCommand gameCommand = gson.fromJson(message, UserGameCommand.class);
        switch (gameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
            case MAKE_MOVE -> makeMoveMessage(session, message);
            case LEAVE -> leave(session, message);
            case RESIGN -> resign(session, message);
        }
        System.out.printf("Received: %s", message);
    }

    private void leave(Session session, String message) throws ResponseException, IOException {
        Gson gson = new Gson();
        Leave leave = gson.fromJson(message, Leave.class);
        String authToken = leave.getAuthString();
        int gameID = leave.getGameID();

        GameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();

        AuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);
        String username = authData.username();


        if (Objects.equals(username, gameData.whiteUsername())) {
            gameDAO.updateGame(new GameData(gameID, null, gameData.blackUsername(), gameData.gameName(), game), username, "WHITE");
        } else if (Objects.equals(username, gameData.blackUsername())) {
            gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), null, gameData.gameName(), game), username, "BLACK");
        }

        List<Session> toRemove = new ArrayList<>();
        List<Session> list = sessions.get(gameID);
        for (Session client : list) {
            if (client.isOpen()) {
                if (!Objects.equals(client, session)) {
                    Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has left the game");
                    String gsonMessage = gson.toJson(notification);
                    client.getRemote().sendString(gsonMessage);
                }
            } else {
                toRemove.add(client);
            }
        }
        toRemove.add(session);
        removeSessions(toRemove, gameID);

    }

    private void resign(Session session, String message) throws ResponseException, IOException {
        Gson gson = new Gson();
        Resign resign = gson.fromJson(message, Resign.class);
        String authToken = resign.getAuthString();
        int gameID = resign.getGameID();

        GameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();

        AuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);
        String username = authData.username();

        if (!game.getState()) {
            List<Session> toRemove = new ArrayList<>();
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: can't resign twice");
            String gsonMessage = gson.toJson(error);

            if (session.isOpen()) {
                session.getRemote().sendString(gsonMessage);
            } else {
                toRemove.add(session);
                removeSessions(toRemove, gameID);
            }
        } else if (!Objects.equals(username, gameData.whiteUsername()) && !Objects.equals(username, gameData.blackUsername())) {
            List<Session> toRemove = new ArrayList<>();
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: can't resign as an observer");
            String gsonMessage = gson.toJson(error);

            if (session.isOpen()) {
                session.getRemote().sendString(gsonMessage);
            } else {
                toRemove.add(session);
                removeSessions(toRemove, gameID);
            }
        } else {
            String color;
            if (Objects.equals(username, gameData.whiteUsername())) {
                color = "WHITE";
            } else {
                color = "BLACK";
            }

            game.endState();
            gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game), username, color);

            List<Session> toRemove = new ArrayList<>();
            List<Session> list = sessions.get(gameID);
            for (Session client : list) {
                if (client.isOpen()) {
                    Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has resigned from the game");
                    String gsonMessage = gson.toJson(notification);
                    client.getRemote().sendString(gsonMessage);
                } else {
                    toRemove.add(client);
                }
            }
            removeSessions(toRemove, gameID);
        }
    }

    private void joinObserver(Session session, String message) throws ResponseException, IOException {
        Gson gson = new Gson();
        JoinObserver joinObserver = gson.fromJson(message, JoinObserver.class);

        int gameID = joinObserver.getGameID();
        GameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(gameID);

        String authToken = joinObserver.getAuthString();
        AuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);

        if (authData == null) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: invalid authToken");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else if (gameData == null) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: non existent game ID");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else {
            List<Session> gameUsers = sessions.get(gameID);
            if (gameUsers == null) {
                sendMessage(session, gameID, gson, gameData);
            } else {
                List<Session> toRemove = new ArrayList<>();
                List<Session> list = sessions.get(gameID);
                list.add(session);
                for (Session user : list) {
                    if (user.isOpen()) {
                        if (Objects.equals(user, session)) {
                            LoadGame loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameData.game());
                            String loadMessage = gson.toJson(loadGame);
                            user.getRemote().sendString(loadMessage);
                        }
                        if (!Objects.equals(user, session)) {
                            try {
                                String username = authData.username();

                                Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " has joined the game as observer");
                                String gsonMessage = gson.toJson(notification);
                                user.getRemote().sendString(gsonMessage);
                            } catch (Exception e) {
                                Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error");
                                String gsonMessage = gson.toJson(error);
                                user.getRemote().sendString(gsonMessage);
                            }
                        }
                    } else {
                        toRemove.add(user);
                    }
                }
                removeSessions(toRemove, gameID);

            }
        }

    }


    private void joinPlayer(Session session, String message) throws IOException, ResponseException {
        Gson gson = new Gson();
        JoinPlayer joinPlayer = gson.fromJson(message, JoinPlayer.class);
        ChessGame.TeamColor playerColor = joinPlayer.getPlayerColor();

        int gameID = joinPlayer.getGameID();
        GameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(gameID);

        String authToken = joinPlayer.getAuthString();
        AuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);

        if (authData == null) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: invalid authToken");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else if (gameData == null) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: non existent game ID");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else if ((playerColor == ChessGame.TeamColor.BLACK) && (!Objects.equals(gameData.blackUsername(), authData.username()))) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: color taken");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else if ((playerColor == ChessGame.TeamColor.WHITE) && (!Objects.equals(gameData.whiteUsername(), authData.username()))) {
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: color taken");
            String gsonMessage = gson.toJson(error);
            session.getRemote().sendString(gsonMessage);
        } else {
            List<Session> clients = sessions.get(gameID);
            if (clients == null) {
                sendMessage(session, gameID, gson, gameData);
            } else {
                List<Session> removeSession = new ArrayList<>();
                List<Session> currentClients = sessions.get(gameID);
                currentClients.add(session);
                for (Session client : currentClients) {
                    if (client.isOpen()) {
                        if (Objects.equals(client, session)) {
                            LoadGame gameLoaded = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameData.game());
                            String sendMessage = gson.toJson(gameLoaded);
                            client.getRemote().sendString(sendMessage);
                        }
                        if (!Objects.equals(client, session)) {
                            try {
                                String user = authData.username();
                                String color = (playerColor == ChessGame.TeamColor.WHITE) ? "WHITE" : "BLACK";


                                Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, user + " has joined the game as " + color);
                                String gsonMessage = gson.toJson(notification);
                                client.getRemote().sendString(gsonMessage);
                            } catch (Exception e) {
                                Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error");
                                String gsonMessage = gson.toJson(error);
                                client.getRemote().sendString(gsonMessage);
                            }
                        }
                    } else {
                        removeSession.add(client);
                    }
                }
                removeSessions(removeSession, gameID);
            }
        }

    }


    public void makeMoveMessage(Session session, String message) throws ResponseException, IOException {
        Gson gson = new Gson();
        MakeMove makeMove = gson.fromJson(message, MakeMove.class);
        ChessMove move = makeMove.getMove();
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();

        int gameID = makeMove.getGameID();
        GameDAO gameDAO = new SQLGameDAO();
        GameData gameData = gameDAO.getGame(gameID);

        ChessGame game = gameData.game();
        ChessGame.TeamColor currentTeam = game.getTeamTurn();
        ChessBoard board = game.getBoard();

        String authToken = makeMove.getAuthString();
        AuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);
        String username = authData.username();

        ChessGame.TeamColor playerColor;
        if (Objects.equals(username, gameData.whiteUsername())) {
            playerColor = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(username, gameData.blackUsername())) {
            playerColor = ChessGame.TeamColor.BLACK;
        } else {
            playerColor = null;
        }

        WSMoveHelper moveHelper = new WSMoveHelper();

        if (!moveHelper.colorChecker(start, board, playerColor)) {
            List<Session> toRemove = new ArrayList<>();
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: unable to move piece.");
            String gsonMessage = gson.toJson(error);
            if (session.isOpen()) {
                session.getRemote().sendString(gsonMessage);
            } else {
                toRemove.add(session);
                removeSessions(toRemove, gameID);
            }
        } else if (!game.getState()) {
            List<Session> toRemove = new ArrayList<>();
            Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: Checkmate or Stalemate.");
            String gsonMessage = gson.toJson(error);

            if (session.isOpen()) {
                session.getRemote().sendString(gsonMessage);
            } else {
                toRemove.add(session);
                removeSessions(toRemove, gameID);
            }
        } else {
            List<Session> toRemove = new ArrayList<>();
            List<Session> gameUsers = sessions.get(gameID);

            String color;
            if (Objects.equals(username, gameData.whiteUsername())) {
                color = "WHITE";
            } else {
                color = "BLACK";
            }
            try {
                game.makeMove(move);
                if (game.isInCheckmate(game.getTeamTurn()) || game.isInStalemate(game.getTeamTurn())) {
                    game.endState();
                }
                gameDAO.updateGame(new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game), username, color);
                for (Session user : gameUsers) {
                    if (user.isOpen()) {
                        LoadGame loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
                        String loadMessage = gson.toJson(loadGame);
                        user.getRemote().sendString(loadMessage);
                        if (!Objects.equals(user, session)) {
                            Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, username + " moved from row: "
                                    + start.getRow() + ", column: " + start.getColumn() + " to row: " + end.getRow()
                                    + ", column: " + end.getColumn());
                            String gsonMessage = gson.toJson(notification);
                            user.getRemote().sendString(gsonMessage);
                        }
                    } else {
                        toRemove.add(user);
                    }
                }
                removeSessions(toRemove, gameID);
            } catch (InvalidMoveException e) {
                Error error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: unable to move piece.");
                String gsonMessage = gson.toJson(error);
                if (session.isOpen()) {
                    session.getRemote().sendString(gsonMessage);
                } else {
                    toRemove.add(session);
                    removeSessions(toRemove, gameID);
                }
            }
        }
    }

    private void sendMessage(Session session, int gameID, Gson gson, GameData gameData) throws IOException {
        List<Session> gameUsers = sessions.get(gameID);
        if (gameUsers == null) {
            List<Session> toRemove = new ArrayList<>();
            sessions.put(gameID, new ArrayList<>());
            List<Session> list = sessions.get(gameID);
            list.add(session);

            LoadGame loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameData.game());
            String loadMessage = gson.toJson(loadGame);
            if (session.isOpen()) {
                session.getRemote().sendString(loadMessage);
            } else {
                toRemove.add(session);
                removeSessions(toRemove, gameID);
            }
        }
    }


    public void removeSessions(List<Session> toRemove, int gameID) {
        List<Session> list = sessions.get(gameID);
        for (Session closedSession : toRemove) {
            list.removeIf(client -> closedSession == client);
        }
    }
}