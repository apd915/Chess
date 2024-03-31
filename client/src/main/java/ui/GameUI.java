package ui;

import ResponseException.ResponseException;
import chess.ChessBoard;
import server.ServerFacade;
import ui.drawGame.DrawBoard;
import ui.drawGame.HighLightHelper;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;

public class GameUI implements ServerMessageObserver {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    ServerFacade server = new ServerFacade();
    int gameID;
    String playerColor;

    public GameUI(int gameID, String playerColor) {
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    DrawBoard drawBoard = new DrawBoard(playerColor);

    public void determineState() throws ResponseException {
        boolean gameLoop = true;

        while (gameLoop) {
            out.print(SET_BG_COLOR_DARK_GREY);
            out.print(SET_TEXT_COLOR_WHITE);

            System.out.printf("[GAME %d] >>> ", gameID);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            var parameters = line.split(" ");

            switch (parameters[0]) {
                case "help":
                    help();
                    break;
                case "redraw":
                    if (Objects.equals(parameters[1], "board")) {
                        redraw();
                    } else {
                        wrongCommand();
                    }
                    break;
                case "leave":
                    // update gameLoop
                    gameLoop = false;
                    break;
                case "make":
                    if (parameters.length != 3) {
                        wrongCommand();
                        break;
                    }
                    if (!coordinateChecker(parameters[1])) {
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.println("invalid coordinates.");
                        break;
                    }
                    if (!coordinateChecker(parameters[2])) {
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.println("invalid coordinates.");
                        break;
                    }
                    makeMove();
                    break;
                case "resign":
                    resign();
                    break;
                case "highlight":
                    if (parameters.length != 2) {
                        wrongCommand();
                        break;
                    }
                    if (!coordinateChecker(parameters[1])) {
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.println("invalid coordinates.");
                        break;
                    }
                    highlightMoves(parameters[1]);
                    break;
                default:
                    wrongCommand();
            }
        }
    }

    private void wrongCommand() {
        out.print(SET_TEXT_COLOR_RED);
        System.out.println("can't recognize command.");
    }

    private void highlightMoves(String coordinate) {
        HighLightHelper helper = new HighLightHelper();
        if (!helper.colorChecker(coordinate, drawBoard.getBoard(), playerColor)) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("piece is blank or not of user's color.");
        } else {

        }



    }

    private void resign() {
    }

    private void makeMove() {

    }

    private void redraw() {
        DrawBoard.drawInitial();
    }

    private void help() {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("redraw board");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - redraws board upon request");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("highlight <PIECE COORDINATE>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - highlights legal moves possible by chosen piece");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("make <COORDINATE FROM> <COORDINATE TO>");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - input desired move on chosen piece");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("help");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - with possible commands");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("resign");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - forfeit and end game");
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("leave");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - exit game");
    }

    private boolean coordinateChecker(String coordinate) {
        Set<Character> letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
        Set<Character> numbers = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
        if (coordinate.length() != 2) {
            return false;
        }
        if (!letters.contains(coordinate.charAt(0))) {
            return false;
        }
        return numbers.contains(coordinate.charAt(1));
    }

    @Override
    public void notify(ServerMessage message) {

    }
}
