package chess;

import java.util.HashSet;

public class BishopMovesHelper {

    public void upRight(HashSet<ChessMove> bishopMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        col++;
        ChessPosition upRight = new ChessPosition(row, col);
        while ((row < 8) && (col < 8) && (board.getPiece(upRight) == null)) {
            bishopMoves.add(new ChessMove(myPosition, upRight, null));
            row++;
            col++;
            upRight = new ChessPosition(row, col);
        }
        if (board.getPiece(upRight) != null) {
            if (board.getPiece(upRight).color != color) {
                bishopMoves.add(new ChessMove(myPosition, upRight, null));
            }
        } else {
            bishopMoves.add(new ChessMove(myPosition, upRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void upLeft(HashSet<ChessMove> bishopMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {

        row++;
        col--;
        ChessPosition upLeft = new ChessPosition(row, col);
        while ((row < 8) && (col > 1) && (board.getPiece(upLeft) == null)) {
            bishopMoves.add(new ChessMove(myPosition, upLeft, null));
            row++;
            col--;
            upLeft = new ChessPosition(row, col);
        }
        if (board.getPiece(upLeft) != null) {
            if (board.getPiece(upLeft).color != color) {
                bishopMoves.add(new ChessMove(myPosition, upLeft, null));
            }
        } else {
            bishopMoves.add(new ChessMove(myPosition, upLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void downRight(HashSet<ChessMove> bishopMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {

        row--;
        col++;
        ChessPosition downRight = new ChessPosition(row, col);
        while ((row > 1) && (col < 8) && (board.getPiece(downRight) == null)) {
            bishopMoves.add(new ChessMove(myPosition, downRight, null));
            row--;
            col++;
            downRight = new ChessPosition(row, col);
        }
        if (board.getPiece(downRight) != null) {
            if (board.getPiece(downRight).color != color) {
                bishopMoves.add(new ChessMove(myPosition, downRight, null));
            }
        } else {
            bishopMoves.add(new ChessMove(myPosition, downRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void downLeft(HashSet<ChessMove> bishopMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {

        row--;
        col--;
        ChessPosition downLeft = new ChessPosition(row, col);
        while ((row > 1) && (col > 1) && (board.getPiece(downLeft) == null)) {
            bishopMoves.add(new ChessMove(myPosition, downLeft, null));
            row--;
            col--;
            downLeft = new ChessPosition(row, col);
        }
        if (board.getPiece(downLeft) != null) {
            if (board.getPiece(downLeft).color != color) {
                bishopMoves.add(new ChessMove(myPosition, downLeft, null));
            }
        } else {
            bishopMoves.add(new ChessMove(myPosition, downLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }
}
