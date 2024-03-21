package ui;

import ResponseException.ResponseException;
import model.UserData;
import server.ServerFacade;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PreLogin {
    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    ServerFacade server = new ServerFacade();

    public PreLogin() {
    }

    public boolean determineState() throws ResponseException {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        System.out.printf("[LOGGED OUT] >>> ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        var parameters = line.split(" ");

        switch (parameters[0]) {
            case "register": {
                return register(parameters);
            }
            case "login": {
                return login(parameters);
            }
            case "help": {
                help();
                break;
            }
            case "quit": {
                return false;
            }
            default:
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("can't recognize command.");

        }

        return true;
    }

    private boolean login(String[] parameters) {
        try {
            if (parameters.length != 3) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect login commands.");
                return true;
            } else {
                out.print(SET_TEXT_COLOR_YELLOW);
                server.login(new UserData(parameters[1], parameters[2], null));
                System.out.print("logged in as ");
                System.out.println(parameters[1]);
                PostLogin postLogin = new PostLogin();
                return postLogin.determineState();
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
            return true;
        }

    }

    private boolean register(String[] parameters) throws ResponseException {
        try {
            if (parameters.length != 4) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("incorrect register commands.");
                return true;
            } else {
                out.print(SET_TEXT_COLOR_YELLOW);
                server.registerUser(new UserData(parameters[1], parameters[2], parameters[3]));
                System.out.print("logged in as ");
                System.out.println(parameters[1]);
                PostLogin postLogin = new PostLogin();
                return postLogin.determineState();
            }
        } catch (ResponseException e) {
            switch (e.StatusCode()) {
                case 400:
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("Bad request.");
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
            return true;
        }


    }

    public void help() {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("register <USERNAME> <PASSWORD> <EMAIL>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to create an account");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("login <USERNAME> <PASSWORD>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to play Chess");
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
