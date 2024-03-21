package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final ChessBoard board = new ChessBoard();
    private static Random rand = new Random();


    public DrawBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

//        out.print(ERASE_SCREEN);

        board.resetBoard();

        drawX(out);
        drawYReversed(out);
        drawX(out);

        setBlack(out);
        out.println(EMPTY);

        drawXReversed(out);
        drawY(out);
        drawXReversed(out);




        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawX(PrintStream out) {

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

    private static void drawXReversed(PrintStream out) {

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

    private static void drawIndX(PrintStream out, String headerText) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(" ");
        out.print(headerText);
        out.print("\u2003");

    }

    private static void drawY(PrintStream out) {
        String[] headers = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for (int j = 0; j < headers.length; j++) {
            drawIndY(out, headers[j]);
            if ((j % 2 == 0)) {
                setWhite(out);
                drawChessBoardRow(out, j + 1, SET_BG_COLOR_LIGHT_GREY, headers[j]);
            } else {
                setBlack(out);
                drawChessBoardRow(out, j + 1, SET_BG_COLOR_DARK_GREEN, headers[j]);
            }

        }
    }

    private static void drawYReversed(PrintStream out) {
        String[] headers = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for (int j = 8; j > 0; j--) {
            drawIndY(out, headers[j-1]);
            if ((j % 2 == 1)) {
                setWhite(out);
                drawChessBoardRowReversed(out, j - 1, SET_BG_COLOR_DARK_GREEN, headers[j-1]);
            } else {
                setBlack(out);
                drawChessBoardRowReversed(out, j - 1, SET_BG_COLOR_LIGHT_GREY, headers[j-1]);
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

    private static void drawChessBoardRow(PrintStream out, int index, String color, String header) {
        int squareColor = 0;
        if (Objects.equals(color, SET_BG_COLOR_LIGHT_GREY)) {
            squareColor = 0;
        }
        if (Objects.equals(color, SET_BG_COLOR_DARK_GREEN)) {
            squareColor = 1;
        }
        for (int i = 0; i < 8; i++) {
            if (squareColor == 0) {
                out.print(SET_BG_COLOR_LIGHT_GREY);
            }
            if (squareColor == 1) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
            ChessPiece piece = board.getPiece(new ChessPosition(index, i + 1));
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

    private static void drawChessBoardRowReversed(PrintStream out, int index, String color, String header) {
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
