package chess;

import java.util.HashSet;

public class KnightMovesHelper {

    public void rightUp(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        col+=2;
        ChessPosition rightUp = new ChessPosition(row, col);
        if ((board.getPiece(rightUp) == null) || (board.getPiece(rightUp).color != color)) {
            knightMoves.add(new ChessMove(myPosition, rightUp, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void rightDown(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row--;
        col+=2;
        ChessPosition rightDown = new ChessPosition(row, col);
        if ((board.getPiece(rightDown) == null) || (board.getPiece(rightDown).color != color)) {
            knightMoves.add(new ChessMove(myPosition, rightDown, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void upRight(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row += 2;
        col++;
        ChessPosition upRight = new ChessPosition(row, col);
        if ((board.getPiece(upRight) == null) || (board.getPiece(upRight).color != color)) {
            knightMoves.add(new ChessMove(myPosition, upRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void upLeft(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row += 2;
        col--;
        ChessPosition upLeft = new ChessPosition(row, col);
        if ((board.getPiece(upLeft) == null) || (board.getPiece(upLeft).color != color)) {
            knightMoves.add(new ChessMove(myPosition, upLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void leftUp(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        col-=2;
        ChessPosition leftUp = new ChessPosition(row, col);
        if ((board.getPiece(leftUp) == null) || (board.getPiece(leftUp).color != color)) {
            knightMoves.add(new ChessMove(myPosition, leftUp, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void leftDown(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row--;
        col-=2;
        ChessPosition leftDown = new ChessPosition(row, col);
        if ((board.getPiece(leftDown) == null) || (board.getPiece(leftDown).color != color)) {
            knightMoves.add(new ChessMove(myPosition, leftDown, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void downRight(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row -= 2;
        col++;
        ChessPosition downRight = new ChessPosition(row, col);
        if ((board.getPiece(downRight) == null) || (board.getPiece(downRight).color != color)) {
            knightMoves.add(new ChessMove(myPosition, downRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void downLeft(HashSet<ChessMove> knightMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row -= 2;
        col--;
        ChessPosition downLeft = new ChessPosition(row, col);
        if ((board.getPiece(downLeft) == null) || (board.getPiece(downLeft).color != color)) {
            knightMoves.add(new ChessMove(myPosition, downLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }
}
