package server.WSHelper;

import chess.*;

import java.util.Collection;
import java.util.Objects;

public class WSMoveHelper {

    public WSMoveHelper() {}

    public boolean colorChecker(ChessPosition start, ChessBoard board, ChessGame.TeamColor color) {
        ChessPiece piece = board.getPiece(start);

        if (piece == null ) {
            return false;
        }
        // returns true if piece is of the color of the user
        return Objects.equals(piece.getTeamColor(), color);
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

    public Collection<ChessMove> selectMoves(String coordinate, ChessBoard board, String color) {
        int row = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        int col = determineCol(coordinate.charAt(0));
//        HashMap<Integer, List<Integer>> movePositions = new HashMap<>();

        ChessPosition position = new ChessPosition(row, col);
        ChessPiece piece = board.getPiece(position);
        Collection<ChessMove> moves = piece.pieceMoves(board, position);

        return moves;
    }

    public Boolean determineEndMove(String coordinateTo, Collection<ChessMove> moves) {
        int row = Integer.parseInt(String.valueOf(coordinateTo.charAt(1)));
        int col = determineCol(coordinateTo.charAt(0));

        ChessPosition position = new ChessPosition(row, col);
        for (ChessMove move : moves) {
            ChessPosition endPosition = move.getEndPosition();
            if (endPosition.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public ChessPosition coordinateConverter(String coordinate) {
        int row = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        int col = colConverter(coordinate.charAt(0));
        return new ChessPosition(row, col);
    }

    public int colConverter(char letter) {
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


}
