package ui;

import ResponseException.ResponseException;
import gameModels.GameName;
import gameModels.JoinGame;
import server.ServerFacade;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLogin {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    ServerFacade server = new ServerFacade();
    public PostLogin() {}

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
//                    observe(parameters);
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

    private void logout(String[] parameters) throws ResponseException {
        if (parameters.length != 1) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("incorrect logout command.");
        } else {
            server.logout();
        }
    }

    private void join(String[] parameters) throws ResponseException {
        if ((parameters.length == 3) || (parameters.length == 2)) {
            try {
                int num = Integer.parseInt(parameters[1]);
                if (parameters[2] == "BLACK ") {

                }
                    server.joinGame(new JoinGame(parameters[2], num));
            } catch (NumberFormatException e) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("<ID> field not a number.");
            }
        } else {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("incorrect join commands.");
        }
    }

    private void list(String[] parameters) throws ResponseException {
        if (parameters.length != 1) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("incorrect list commands.");
        } else {
            server.listGames();
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

    private void create(String[] parameters) throws ResponseException {
        if (parameters.length != 2) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("incorrect create commands.");
        } else {
            server.createGame(new GameName(parameters[1]));
            DrawBoard drawBoard = new DrawBoard();
        }


    }

}
