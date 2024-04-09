package ui.drawGame;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.*;

public class HighLightHelper {
    public HighLightHelper() {}

    public boolean colorChecker(String coordinate, ChessBoard board, String color) {
        int row = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        int col = determineCol(coordinate.charAt(0));
        ChessPiece piece = board.getPiece(new ChessPosition(row, col));

        if (piece == null ) {
            return false;
        }
        // returns true if piece is of the color of the user
        return Objects.equals(piece.teamColorString(piece.getTeamColor()), color);
    }

    static int determineCol(char letter) {
        switch (letter) {
            case 'a':
                return 8;
            case 'b':
                return 7;
            case 'c':
                return 6;
            case 'd':
                return 5;
            case 'e':
                return 4;
            case 'f':
                return 3;
            case 'g':
                return 2;
            case 'h':
                return 1;
        }
        return 0;
    }

    public static void selectMoves(String coordinate, ChessBoard board, String color) {
        int row = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        int col = determineCol(coordinate.charAt(0));
        HashMap<Integer, List<Integer>> movePositions = new HashMap<>();

        ChessPosition position = new ChessPosition(row, col);
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> moves = piece.pieceMoves(board, position);

        for (ChessMove move : moves) {
            ChessPosition endPosition = move.getEndPosition();
            if (movePositions.containsKey(endPosition.getRow())) {
                movePositions.get(endPosition.getRow()).add(endPosition.getColumn());
            } else {
                movePositions.put(endPosition.getRow(), new ArrayList<>());
                movePositions.get(endPosition.getRow()).add(endPosition.getColumn());
            }
        }
        new DrawBoard(color);
        DrawBoard.highlight(movePositions, color);
    }


}
