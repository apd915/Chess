package ui;

import ResponseException.ResponseException;
import WebSocket.WSClient;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.GameDAO;
import dataAccess.SQLDAO.SQLGameDAO;
import gameModels.*;
import model.GameData;
import server.ServerFacade;
import ui.drawGame.DrawBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.Session;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLogin {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    ServerFacade server = new ServerFacade();

    ChessGame game = new ChessGame();

    public PostLogin() {
    }

    public boolean determineState() throws ResponseException {
        while (true) {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_WHITE);
            System.out.printf("[LOGGED IN] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var parameters = line.split(" ");

            switch (parameters[0]) {
                case "create": {
                    create(parameters);
                    break;
                }
                case "list": {
                    list(parameters);
                    break;
                }
                case "join": {
                    join(parameters);
                    break;
                }
                case "observe": {
                    // difference between join with empty and observe?
                    observe(parameters);
                    break;
                }
                case "logout": {
                    logout(parameters);
                    return true;
                }
                case "quit": {
                    return false;
                }
                case "help": {
                    help();
                    break;
                }
                default:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("can't recognize command.");
            }
        }
    }

    private void observe(String[] parameters) {
        try {
            if (parameters.length != 2) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect observe commands.");
            } else {
                try {
                    int num = Integer.parseInt(parameters[1]);
                    GameUI gameUI = new GameUI(num, null, game, server.getToken());
                    server.joinGame(new JoinGame(null, num));
                    try {
                        WSClient ws = new WSClient();

                        ws.observe(server.getToken(), gameUI.gameID);
                        gameUI.determineState();
                    } catch (Exception e) {
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.println("Unable to join game.");
                    }
                } catch (NumberFormatException e) {
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("<ID> field not a number.");
                }
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 400:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Bad request.");
                    break;
                case 401:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unauthorized.");
                    break;
                case 403:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Username taken.");
                    break;
                case 500:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unexpected error.");
                    break;
            }
        }
    }

    private void logout(String[] parameters) {
        try {
            if (parameters.length != 1) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect logout command.");
            } else {
                server.logout();
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 401:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unauthorized.");
                    break;
                case 500:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unexpected error.");
                    break;
            }
        }
    }

    private void create(String[] parameters) {

        try {
            if (parameters.length != 2) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect create commands.");
            } else {
                GameID gameID = server.createGame(new GameName(parameters[1]));
                game.getBoard().resetBoard();
                out.print(SET_TEXT_COLOR_YELLOW);
                System.out.print("Your assigned ID is: ");
                System.out.println(gameID.gameID());
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 400:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Bad request.");
                    break;
                case 401:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unauthorized.");
                    break;
                case 500:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unexpected error.");
                    break;
            }
        }
    }

    private void join(String[] parameters) {
        try {
            if ((parameters.length == 3) || (parameters.length == 2)) {
                try {
                    int num = Integer.parseInt(parameters[1]);
                    if (parameters.length == 3) {
                        if (!Objects.equals(parameters[2], "WHITE") || !Objects.equals(parameters[2], "BLACK")) {
                            GameUI gameUI = new GameUI(num, parameters[2], game, server.getToken());
                            server.joinGame(new JoinGame(parameters[2], num));
                            new DrawBoard(parameters[2]);
//                            DrawBoard.drawInitial(parameters[2]);
                            DrawBoard.drawInitial(parameters[2]);
                            try {
                                WSClient ws = new WSClient();

                                ChessGame.TeamColor teamColor = (Objects.equals(parameters[2], "WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                                ws.joinPlayer(server.getToken(), gameUI.gameID, teamColor);
                                gameUI.determineState();
                            } catch (Exception e) {
                                out.print(SET_TEXT_COLOR_RED);
                                System.out.println("Unable to join game.");
                            }

                        }
                    } else {
                        GameUI gameUI = new GameUI(num, null, game, server.getToken());
                        server.joinGame(new JoinGame(null, num));
                        new DrawBoard(null);
                        DrawBoard.drawInitial("WHITE");
//                        DrawBoard.drawInitial("WHITE");
                        gameUI.determineState();
//                        new DrawBoard();
                    }

                } catch (NumberFormatException e) {
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("<ID> field not a number.");
                }
            } else {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect join commands.");
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 400:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Bad request.");
                    break;
                case 401:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unauthorized.");
                    break;
                case 403:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Username taken.");
                    break;
                case 500:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unexpected error.");
                    break;
            }
        }
    }

    private void list(String[] parameters) throws ResponseException {
        try {
            if (parameters.length != 1) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect list commands.");
            } else {
                ListGamesModel games = server.listGames();
                for (ListGamesInfo game : games.games()) {
                    out.print(SET_TEXT_COLOR_YELLOW);
                    System.out.print("Game ID: ");
                    System.out.print(game.gameID());
                    System.out.print(". White User: ");
                    System.out.print(game.whiteUsername());
                    System.out.print(". Black User: ");
                    System.out.print(game.blackUsername());
                    System.out.print(". Game Name: ");
                    System.out.println(game.gameName());
                }
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 401:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unauthorized.");
                    break;
                case 500:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Unexpected error.");
                    break;
            }
        }
    }

    private void help() {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("create <NAME>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - a game");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("list");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - games");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("join <ID> [WHITE|BLACK|<empty>]");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - a game");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("observe <ID>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - a game");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("logout");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - when you are done");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("quit");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - playing Chess");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("help");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - with possible commands");
    }

}
