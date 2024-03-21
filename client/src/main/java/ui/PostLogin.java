package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class PostLogin {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    public PostLogin() {}

    public boolean determineState() {
        while (true) {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_WHITE);
            System.out.printf("[LOGGED IN] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var parameters = line.split(" ");

            switch (parameters[0]) {
                case "create": {
//                    create(parameters);
                    break;
                }
                case "list": {
//                    list(parameters);
                    break;
                }
                case "join": {
//                    join(parameters);
                    break;
                }
                case "observe": {
//                    observe(parameters);
                    break;
                }
                case "logout": {
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

    public void help() {
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
