package ui.drawGame;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Collection;
import java.util.Objects;

public class HighLightHelper {
    public HighLightHelper() {}

    public boolean colorChecker(String coordinate, ChessBoard board, String color) {
        int row = determineRow(coordinate.charAt(0));
        int col = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        ChessPiece piece = board.getPiece(new ChessPosition(row, col));

        if (piece == null ) {
            return false;
        }
        // returns true if piece is of the color of the user
        return Objects.equals(piece.teamColorString(piece.getTeamColor()), color);
    }

    private static int determineRow(char letter) {
        switch (letter) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
        }
        return 0;
    }

    public static void selectColors(String coordinate, ChessBoard board) {
        int row = determineRow(coordinate.charAt(0));
        int col = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        ChessPosition position = new ChessPosition(row, col);
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> moves = piece.pieceMoves(board, position);

    }

}
