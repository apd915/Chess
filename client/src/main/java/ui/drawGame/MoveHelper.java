package ui.drawGame;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static ui.drawGame.HighLightHelper.determineCol;

public class MoveHelper {

    public MoveHelper() {}


    public Collection<ChessMove> selectMoves(String coordinate, ChessBoard board, String color) {
        int row = Integer.parseInt(String.valueOf(coordinate.charAt(1)));
        int col = determineCol(coordinate.charAt(0));

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
