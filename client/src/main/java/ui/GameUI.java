package ui;

import ResponseException.ResponseException;
import SessionMessages.ServerMessageObserver;
import WebSocket.WSClient;
import chess.*;
import ui.drawGame.DrawBoard;
import ui.drawGame.HighLightHelper;
import ui.drawGame.MoveHelper;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;

public class GameUI implements ServerMessageObserver {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    int gameID;

    String playerColor;
    ChessGame game;
    String authToken;
    WSClient webSocket;


    public GameUI(int gameID, String playerColor, ChessGame game, String authToken) {
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.game = game;
        this.authToken = authToken;
//        game = new ChessGame();
//        game.getBoard().resetBoard();
    }

    DrawBoard drawBoard = new DrawBoard(playerColor);

    public void determineState() throws ResponseException {
        try {
            webSocket = new WSClient();
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
                        leave();
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
                        makeMove(parameters[1], parameters[2]);
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
        } catch (Exception e) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("Unable to join game");;
        }
    }

    private void leave() {
        webSocket.leave(authToken, gameID);
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
            HighLightHelper.selectMoves(coordinate, drawBoard.getBoard(), playerColor);

        }



    }

    private void resign() {
        webSocket.resign(authToken, gameID);
    }

    private void makeMove(String coordinateFrom, String coordinateTo) {
        try {
            MoveHelper helper = new MoveHelper();
            HighLightHelper highLightHelper = new HighLightHelper();
            if (!highLightHelper.colorChecker(coordinateFrom, drawBoard.getBoard(), playerColor)) {
                out.print(SET_TEXT_COLOR_RED);
                System.out.println("piece is blank or not of user's color.");
            } else {
                Collection<ChessMove> moves = helper.selectMoves(coordinateFrom, drawBoard.getBoard(), playerColor);

                if (helper.determineEndMove(coordinateTo, moves)) {


                    ChessPosition start = helper.coordinateConverter(coordinateFrom);
                    ChessPosition end = helper.coordinateConverter(coordinateTo);
                    ChessMove move = new ChessMove(start, end, null);

                    webSocket.makeMoves(authToken, gameID, move);

                } else {
                    out.print(SET_TEXT_COLOR_RED);
                    System.out.println("invalid move.");
                }
            }
        } catch (NullPointerException e) {
            out.print(SET_TEXT_COLOR_RED);
            System.out.println("invalid move.");
        }

    }

    private void redraw() {
        drawBoard.drawMove(game.getBoard(), playerColor);
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
