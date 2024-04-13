package WebSocket;

import ResponseException.ResponseException;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;

//import dataAccess.GameDAO;
//import dataAccess.SQLDAO.SQLGameDAO;

import model.GameData;
import ui.drawGame.DrawBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.net.URI;

public class WSClient extends Endpoint {

    private Session session;
//    WSClient ws = new WSClient();

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                Gson gson = new Gson();
                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> notification(message);
                    case LOAD_GAME -> loadGame(message);
                    case ERROR -> caughtError(message);
                }
            }
        });
    }

    private void caughtError(String message) {
        Gson gson = new Gson();
        Error error = gson.fromJson(message, Error.class);
        System.out.println(error.getErrorMessage());
    }

    private void notification(String message) {
        Gson gson = new Gson();
        Notification notification = gson.fromJson(message, Notification.class);
        System.out.println(notification.getMessage());
    }

    private void loadGame(String message) {
        Gson gson = new Gson();
        LoadGame loadGame = gson.fromJson(message, LoadGame.class);
        ChessGame chessGame = loadGame.getGame();
        ChessGame.TeamColor currentTeam = chessGame.getTeamTurn();
        String color;
        if (currentTeam == ChessGame.TeamColor.WHITE) {
            color = "WHITE";
        } else if (currentTeam == ChessGame.TeamColor.BLACK) {
            color = "BLACK";
        } else {
            color = "WHITE";
        }
        System.out.println();
        DrawBoard.drawMove(chessGame.getBoard(), color);
    }

    public void joinPlayer(String authToken, int gameID, ChessGame.TeamColor color) {
        try {
            Gson gson = new Gson();
            JoinPlayer joinPlayer = new JoinPlayer(authToken, UserGameCommand.CommandType.JOIN_PLAYER, gameID, color);
            String message = gson.toJson(joinPlayer);
            this.send(message);
        } catch (Exception e) {
            System.out.println("error: unable to join game.");
        }
    }

    public void observe(String authToken, int gameID) {
        try {
            Gson gson = new Gson();
            JoinObserver joinObserver = new JoinObserver(authToken, UserGameCommand.CommandType.JOIN_OBSERVER, gameID);
            String message = gson.toJson(joinObserver);
            this.send(message);
        } catch (Exception e) {
            System.out.println("error: unable to join game.");
        }
    }

    public void makeMoves(String authToken, int gameID, ChessMove move) {
        try {
            Gson gson = new Gson();
            MakeMove moveString = new MakeMove(authToken, UserGameCommand.CommandType.MAKE_MOVE, gameID, move);
            String toGson = gson.toJson(moveString);
            send(toGson);
        } catch (Exception e) {
            System.out.println("error: unable to make move.");
        }
    }

    public void resign(String authToken, int gameID) {
        try {
            Gson gson = new Gson();
            Resign resign = new Resign(authToken, UserGameCommand.CommandType.RESIGN, gameID);
            String message = gson.toJson(resign);
            send(message);
        } catch (Exception e) {
            System.out.println("error: unable to make move.");
        }
    }

    public void leave(String authToken, int gameID) {
        try {
            Gson gson = new Gson();
            Leave leave = new Leave(authToken, UserGameCommand.CommandType.LEAVE, gameID);
            String message = gson.toJson(leave);
            send(message);
        } catch (Exception e) {
            System.out.println("error: unable to make move.");
        }
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public Session getSession() {
        return session;
    }
}