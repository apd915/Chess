package WebSocket;

import SessionMessages.MakeMoveMessage;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

import static ui.EscapeSequences.SET_TEXT_COLOR_RED;

public class WSClient extends Endpoint {

    private Session session;
//    WSClient ws = new WSClient();

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
//                Gson gson = new Gson();
//                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
//                switch (serverMessage.getServerMessageType()) {
//                    case NOTIFICATION -> notification(message);
//                    case LOAD_GAME -> loadGame(message);
//                    case ERROR -> errorReceived(message);
//                }
            }
        });
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

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public Session getSession() {
        return session;
    }
}