package ui.drawGame;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class DrawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final ChessBoard board = new ChessBoard();
    private static String playerColor;


    public DrawBoard(String playerColor) {
        DrawBoard.playerColor = playerColor;
    }

    public static void drawInitial(String color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        board.resetBoard();

        if (Objects.equals(color, "BLACK")) {
            drawXBlack(out);
            drawYBlack(out);
            drawXBlack(out);
        } else {
            drawXWhite(out);
            drawYWhite(out);
            drawXWhite(out);
        }

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawXWhite(PrintStream out) {

        setGray(out);
        out.print("   ");
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawIndX(out, headers[boardCol]);
        }
        out.print("   ");
        setBlack(out);
        out.println(EMPTY);
    }

    private static void drawXBlack(PrintStream out) {

        setGray(out);
        out.print("   ");
        String[] headers = {"h", "g", "f", "e", "d", "c", "b", "a"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawIndX(out, headers[boardCol]);
        }
        out.print("   ");
        setBlack(out);
        out.println(EMPTY);
    }

    private static void drawIndX(PrintStream out, String headerText) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(" ");
        out.print(headerText);
        out.print("\u2003");

    }

    private static void drawYBlack(PrintStream out) {
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int j = 0; j < headers.length; j++) {
            drawIndY(out, headers[j]);
            if ((j % 2 == 0)) {
                setWhite(out);
                drawChessBoardRowBlack(out, j + 1, SET_BG_COLOR_LIGHT_GREY, headers[j]);
            } else {
                setBlack(out);
                drawChessBoardRowBlack(out, j + 1, SET_BG_COLOR_DARK_GREEN, headers[j]);
            }

        }
    }

    private static void drawYWhite(PrintStream out) {
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int j = 8; j > 0; j--) {
            drawIndY(out, headers[j-1]);
            if ((j % 2 == 1)) {
                setWhite(out);
                drawChessBoardRowWhite(out, j - 1, SET_BG_COLOR_DARK_GREEN, headers[j-1]);
            } else {
                setBlack(out);
                drawChessBoardRowWhite(out, j - 1, SET_BG_COLOR_LIGHT_GREY, headers[j-1]);
            }

        }
    }

    private static void drawIndY(PrintStream out, String headerText) {
        setGray(out);
        out.print(" ");
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(headerText);
        out.print(" ");
    }

    private static void drawChessBoardRowBlack(PrintStream out, int index, String color, String header) {
        int squareColor = 0;
        if (Objects.equals(color, SET_BG_COLOR_LIGHT_GREY)) {
            squareColor = 0;
        }
        if (Objects.equals(color, SET_BG_COLOR_DARK_GREEN)) {
            squareColor = 1;
        }
        for (int i = 8; i > 0; i--) {
            if (squareColor == 0) {
                out.print(SET_BG_COLOR_LIGHT_GREY);
            }
            if (squareColor == 1) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
            ChessPiece piece = board.getPiece(new ChessPosition(index, i));
            if (piece != null) {
                squareColor = printPiece(piece, out, squareColor);
            } else {
                out.print(EMPTY);
                if (squareColor == 0) squareColor = 1;
                else squareColor = 0;
            }
        }
        drawIndY(out, header);
        setBlack(out);
        out.println(EMPTY);
    }

    private static void drawChessBoardRowWhite(PrintStream out, int index, String color, String header) {
        int squareColor = 0;
        if (Objects.equals(color, SET_BG_COLOR_LIGHT_GREY)) {
            squareColor = 0;
        }
        if (Objects.equals(color, SET_BG_COLOR_DARK_GREEN)) {
            squareColor = 1;
        }
        for (int i = 1; i < 9; i++) {
            if (squareColor == 0) {
                out.print(SET_BG_COLOR_LIGHT_GREY);
            }
            if (squareColor == 1) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
            ChessPiece piece = board.getPiece(new ChessPosition(index+1, i));
            if (piece != null) {
                squareColor = printPiece(piece, out, squareColor);
            } else {
                out.print(EMPTY);
                if (squareColor == 0) squareColor = 1;
                else squareColor = 0;
            }
        }
        drawIndY(out, header);
        setBlack(out);
        out.println(EMPTY);
    }

    private static int printPiece(ChessPiece piece, PrintStream out, int squareColor) {
        switch (piece.getPieceType()) {
            case ROOK -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_ROOK);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_ROOK);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
            case BISHOP -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_BISHOP);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_BISHOP);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
            case KNIGHT -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_KNIGHT);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_KNIGHT);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
            case PAWN -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_PAWN);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_PAWN);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
            case QUEEN -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_QUEEN);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_QUEEN);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
            case KING -> {
                if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    out.print(SET_TEXT_COLOR_BLACK);
                    out.print(BLACK_KING);
                }
                else {
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_KING);
                }
                if (squareColor == 0) return 1;
                else return 0;
            }
        }
        return 0;
    }

    public static void highlight(HashMap<Integer, List<Integer>> movePositions, String color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        if (Objects.equals(color, "BLACK")) {
            drawXBlack(out);
            highlightYBlack(out, movePositions);
            drawXBlack(out);
        } else {
            drawXWhite(out);
            highlightYWhite(out, movePositions);
            drawXWhite(out);
        }

    }

    private static void highlightYBlack(PrintStream out, HashMap<Integer, List<Integer>> movePositions) {
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int j = 0; j < headers.length; j++) {
            drawIndY(out, headers[j]);
            if ((j % 2 == 0)) {
                setWhite(out);
                highlightBoardRowBlack(out, j + 1, SET_BG_COLOR_LIGHT_GREY, headers[j], movePositions);
            } else {
                setBlack(out);
                highlightBoardRowBlack(out, j + 1, SET_BG_COLOR_DARK_GREEN, headers[j], movePositions);
            }

        }
    }

    private static void highlightYWhite(PrintStream out, HashMap<Integer, List<Integer>> movePositions) {
        String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int j = 8; j > 0; j--) {
            drawIndY(out, headers[j-1]);
            if ((j % 2 == 1)) {
                setWhite(out);
                highlightBoardRowWhite(out, j-1, SET_BG_COLOR_DARK_GREEN, headers[j-1], movePositions);
            } else {
                setBlack(out);
                highlightBoardRowWhite(out, j-1, SET_BG_COLOR_LIGHT_GREY, headers[j-1], movePositions);
            }

        }
    }

    private static void highlightBoardRowBlack(PrintStream out, int index, String color, String header, HashMap<Integer, List<Integer>> movePositions) {
        int squareColor = 0;
        if (Objects.equals(color, SET_BG_COLOR_LIGHT_GREY)) {
            squareColor = 0;
        }
        if (Objects.equals(color, SET_BG_COLOR_DARK_GREEN)) {
            squareColor = 1;
        }
        for (int i = 8; i > 0; i--) {
            int j = fixIndex(i);
            if (squareColor == 0) {
                out.print(SET_BG_COLOR_LIGHT_GREY);
            }
            if (squareColor == 1) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
            if (movePositions.get(index) != null) {
                if (movePositions.get(index).contains(j)) {
                    if (squareColor == 0) {
                        out.print(SET_BG_COLOR_MAGENTA);
                    } else {
                        out.print(SET_BG_COLOR_BLUE);
                    }
                }
            }

            ChessPiece piece = board.getPiece(new ChessPosition(index, i));
            if (piece != null) {
                squareColor = printPiece(piece, out, squareColor);
            } else {
                out.print(EMPTY);
                if (squareColor == 0) squareColor = 1;
                else squareColor = 0;
            }
        }
        drawIndY(out, header);
        setBlack(out);
        out.println(EMPTY);
    }

    private static void highlightBoardRowWhite(PrintStream out, int index, String color, String header, HashMap<Integer, List<Integer>> movePositions) {
        int squareColor = 0;
        if (Objects.equals(color, SET_BG_COLOR_LIGHT_GREY)) {
            squareColor = 0;
        }
        if (Objects.equals(color, SET_BG_COLOR_DARK_GREEN)) {
            squareColor = 1;
        }
        for (int i = 1; i < 9; i++) {
            int j = fixIndex(i);
            if (squareColor == 0) {
                out.print(SET_BG_COLOR_LIGHT_GREY);
            }
            if (squareColor == 1) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
            if (movePositions.get(index+1) != null) {
                if (movePositions.get(index+1).contains(j)) {
                    if (squareColor == 0) {
                        out.print(SET_BG_COLOR_MAGENTA);
                    } else {
                        out.print(SET_BG_COLOR_BLUE);
                    }
                }
            }

            ChessPiece piece = board.getPiece(new ChessPosition(index+1, i));
            if (piece != null) {
                squareColor = printPiece(piece, out, squareColor);
            } else {
                out.print(EMPTY);
                if (squareColor == 0) squareColor = 1;
                else squareColor = 0;
            }
        }
        drawIndY(out, header);
        setBlack(out);
        out.println(EMPTY);
    }

    private static int fixIndex(int i) {
        return switch (i) {
            case 8 -> 1;
            case 7 -> 2;
            case 6 -> 3;
            case 5 -> 4;
            case 4 -> 5;
            case 3 -> 6;
            case 2 -> 7;
            case 1 -> 8;
            default -> 0;
        };
    }
    public ChessBoard getBoard() {
        return board;
    }


    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setGray(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_BG_COLOR_DARK_GREY);
    }



}
