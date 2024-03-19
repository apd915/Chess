package ui;

import ResponseException.ResponseException;
import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
//    private String visitorName = null;
//    private final ServerFacade server;
//    private final String serverUrl;
//    private State state = State.SIGNEDOUT;
//
//    public ChessClient(String serverUrl, NotificationHandler notificationHandler) {
//        server = new ServerFacade(serverUrl);
//        this.serverUrl = serverUrl;
//        this.notificationHandler = notificationHandler;
//    }
//
//    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "register" -> register(params);
//                case "login" -> login(params);
//                case "create" -> create(params);
//                case "list" -> list();
//                case "join" -> join(params);
//                case "observe" -> observe(params);
//                case "logout" -> logout();
//                case "quit" -> "quit";
//                default -> help();
//            };
//        }
//        catch (ResponseException ex) {
//            return ex.getMessage();
//        }
//    }
//
//    private String login(String[] params) throws ResponseException {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//            ws = new WebSocketFacade(serverUrl, notificationHandler);
//
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//        throw new ResponseException(400, "Expected: <yourname>");
//    }
//
//    public String help() {
//        if (state == State.SIGNEDOUT) {
//            return """
//                    - register <USERNAME> <PASSWORD> <EMAIL>
//                    - login <USERNAME> <PASSWORD>
//                    - quit
//                    - help
//                    """;
//        }
//        return """
//                - create <NAME>
//                - list
//                - join <ID> [WHITE|BLACK|<empty>]
//                - observe <ID>
//                - logout
//                - quit
//                - help
//                """;
//    }
//
//    private void assertSignedIn() throws ResponseException {
//        if (state == State.SIGNEDOUT) {
//            throw new ResponseException(400, "You must sign in");
//        }
//    }
}
