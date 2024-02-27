package chess;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class RookMovesHelper {

    public void moves(HashSet<ChessMove> rookMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        if (row < 8) {
            up(rookMoves, board, myPosition, row, color);
        }
        if (row > 1) {
            down(rookMoves, board, myPosition, row, color);
        }
        if (col < 8) {
            right(rookMoves, board, myPosition, col, color);
        }
        if (col > 1) {
            left(rookMoves, board, myPosition, col, color);
        }
    }

    public void up(HashSet<ChessMove> rookMoves, ChessBoard board, ChessPosition myPosition, int row, ChessGame.TeamColor color) {
        row++;
        ChessPosition upPosition = new ChessPosition(row, myPosition.getColumn());
        while ((row < 8) && (board.getPiece(upPosition) == null)) {
            rookMoves.add(new ChessMove(myPosition, upPosition, null));
            row++;
            upPosition = new ChessPosition(row, myPosition.getColumn());
        }
        if (board.getPiece(upPosition) != null) {
            if ((board.getPiece(upPosition).color) != color) {
                rookMoves.add(new ChessMove(myPosition, upPosition, null));
            }
        } else {
            rookMoves.add(new ChessMove(myPosition, upPosition, null));
        }
        row = myPosition.getRow();

    }

    public void down(HashSet<ChessMove> rookMoves, ChessBoard board, ChessPosition myPosition, int row, ChessGame.TeamColor color) {
        row--;
        ChessPosition downPosition = new ChessPosition(row, myPosition.getColumn());
        while ((row > 1) && (board.getPiece(downPosition) == null)) {
            rookMoves.add(new ChessMove(myPosition, downPosition, null));
            row--;
            downPosition = new ChessPosition(row, myPosition.getColumn());
        }
        if ((board.getPiece(downPosition) != null)) {
            if ((board.getPiece(downPosition).color) != (board.getPiece(myPosition).color)) {
                rookMoves.add(new ChessMove(myPosition, downPosition, null));
            }
        } else {
            rookMoves.add(new ChessMove(myPosition, downPosition, null));
        }
        row = myPosition.getRow();
    }

    public void right(HashSet<ChessMove> rookMoves, ChessBoard board, ChessPosition myPosition, int col, ChessGame.TeamColor color){
        col++;
        ChessPosition rightPosition = new ChessPosition(myPosition.getRow(), col);
        while ((col < 8) && (board.getPiece(rightPosition) == null)) {
            rookMoves.add(new ChessMove(myPosition, rightPosition, null));
            col++;
            rightPosition = new ChessPosition(myPosition.getRow(), col);
        }
        if ((board.getPiece(rightPosition) != null)) {
            if ((board.getPiece(rightPosition).color) != (board.getPiece(myPosition).color)) {
                rookMoves.add(new ChessMove(myPosition, rightPosition, null));
            }
        } else {
            rookMoves.add(new ChessMove(myPosition, rightPosition, null));
        }
        col = myPosition.getColumn();
    }

    public void left(HashSet<ChessMove> rookMoves, ChessBoard board, ChessPosition myPosition, int col, ChessGame.TeamColor color) {
        col--;
        ChessPosition leftPosition = new ChessPosition(myPosition.getRow(), col);
        while ((col > 1) && (board.getPiece(leftPosition) == null)) {
            rookMoves.add(new ChessMove(myPosition, leftPosition, null));
            col--;
            leftPosition = new ChessPosition(myPosition.getRow(), col);
        }
        if ((board.getPiece(leftPosition) != null)) {
            if ((board.getPiece(leftPosition).color) != (board.getPiece(myPosition).color)) {
                rookMoves.add(new ChessMove(myPosition, leftPosition, null));
            }
        } else {
            rookMoves.add(new ChessMove(myPosition, leftPosition, null));
        }
        col = myPosition.getColumn();
    }
}
