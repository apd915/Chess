package chess;

import java.util.HashSet;

public class PawnKingHelper {

    public void kingUp(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        ChessPosition up = new ChessPosition(row, col);
        if ((board.getPiece(up) == null) || (board.getPiece(up).color != color)) {
            kingMoves.add(new ChessMove(myPosition, up, null));
        }
        row = myPosition.getRow();
    }

    public void kingDown(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row--;
        ChessPosition down = new ChessPosition(row, col);
        if ((board.getPiece(down) == null) || (board.getPiece(down).color != color)) {
            kingMoves.add(new ChessMove(myPosition, down, null));
        }
        row = myPosition.getRow();
    }

    public void kingLeft(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        col--;
        ChessPosition left = new ChessPosition(row, col);
        if ((board.getPiece(left) == null) || (board.getPiece(left).color != color)) {
            kingMoves.add(new ChessMove(myPosition, left, null));
        }
        col = myPosition.getColumn();
    }

    public void kingRight(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        col++;
        ChessPosition right = new ChessPosition(row, col);
        if ((board.getPiece(right) == null) || (board.getPiece(right).color != color)) {
            kingMoves.add(new ChessMove(myPosition, right, null));
        }
        col = myPosition.getColumn();
    }

    public void kingUpRight(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        col++;
        ChessPosition upRight = new ChessPosition(row, col);
        if ((board.getPiece(upRight) == null) || (board.getPiece(upRight).color != color)) {
            kingMoves.add(new ChessMove(myPosition, upRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void kingUpLeft(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row++;
        col--;
        ChessPosition upLeft = new ChessPosition(row, col);
        if ((board.getPiece(upLeft) == null) || (board.getPiece(upLeft).color != color)) {
            kingMoves.add(new ChessMove(myPosition, upLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void kingDownRight(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row--;
        col++;
        ChessPosition downRight = new ChessPosition(row, col);
        if ((board.getPiece(downRight) == null) || (board.getPiece(downRight).color != color)) {
            kingMoves.add(new ChessMove(myPosition, downRight, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void kingDownLeft(HashSet<ChessMove> kingMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        row--;
        col--;
        ChessPosition downLeft = new ChessPosition(row, col);
        if ((board.getPiece(downLeft) == null) || (board.getPiece(downLeft).color != color)) {
            kingMoves.add(new ChessMove(myPosition, downLeft, null));
        }
        row = myPosition.getRow();
        col = myPosition.getColumn();
    }

    public void pawnWhite(HashSet<ChessMove> pawnMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        ChessPosition front = new ChessPosition(row+1, col);
        if (row == 2) {
            ChessPosition twoFront = new ChessPosition(row + 2, col);
            if (board.getPiece(front) == null) {
                pawnMoves.add(new ChessMove(myPosition, front, null));
                if (board.getPiece(twoFront) == null) {
                    pawnMoves.add(new ChessMove(myPosition, twoFront, null));
                }
            }
        }
        if ((board.getPiece(front) == null) && (row+1 != 8)) {
            pawnMoves.add(new ChessMove(myPosition, front, null));
        }
        if ((row<8) && (col<8)) {
            ChessPosition diagonalRight = new ChessPosition(row+1,col+1);
            if (board.getPiece(diagonalRight) != null) {
                if ((board.getPiece(diagonalRight).color != color) && (row+1 == 8)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.QUEEN));
                }
                if ((board.getPiece(diagonalRight).color != color) && (row+1 != 8)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, null));
                }
            }
        }
        if ((row<8) && (col>1)) {
            ChessPosition diagonalLeft = new ChessPosition(row+1,col-1);
            if (board.getPiece(diagonalLeft) != null) {
                if ((board.getPiece(diagonalLeft).color != color) && (row+1 == 8)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.QUEEN));
                }
                if ((board.getPiece(diagonalLeft).color != color) && (row+1 != 8)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, null));
                }
            }
        }
        if (row+1 == 8) {
            ChessPosition lastRow = new ChessPosition(row+1,col);
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.ROOK));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.BISHOP));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.KNIGHT));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.QUEEN));
        }
    }

    public void pawnBlack(HashSet<ChessMove> pawnMoves, ChessBoard board, ChessPosition myPosition, int row, int col, ChessGame.TeamColor color) {
        ChessPosition front = new ChessPosition(row-1, col);
        if (row == 7) {
            ChessPosition twoFront = new ChessPosition(row - 2, col);
            if (board.getPiece(front) == null) {
                pawnMoves.add(new ChessMove(myPosition, front, null));
                if (board.getPiece(twoFront) == null) {
                    pawnMoves.add(new ChessMove(myPosition, twoFront, null));
                }
            }
        }
        if ((board.getPiece(front) == null) && (row-1 != 1)) {
            pawnMoves.add(new ChessMove(myPosition, front, null));
        }
        if ((row>1) && (col<8)) {
            ChessPosition diagonalRight = new ChessPosition(row-1,col+1);
            if (board.getPiece(diagonalRight) != null) {
                if ((board.getPiece(diagonalRight).color != color) && (row-1 == 1)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, ChessPiece.PieceType.QUEEN));
                }
                if ((board.getPiece(diagonalRight).color != color) && (row-1 != 1)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalRight, null));
                }
            }
        }
        if ((row>1) && (col>1)) {
            ChessPosition diagonalLeft = new ChessPosition(row-1,col-1);
            if (board.getPiece(diagonalLeft) != null) {
                if ((board.getPiece(diagonalLeft).color != color) && (row-1 ==1)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, ChessPiece.PieceType.QUEEN));
                }
                if ((board.getPiece(diagonalLeft).color != color) && (row-1!=1)) {
                    pawnMoves.add(new ChessMove(myPosition, diagonalLeft, null));
                }
            }
        }
        if (row-1 == 1) {
            ChessPosition lastRow = new ChessPosition(row-1,col);
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.ROOK));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.BISHOP));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.KNIGHT));
            pawnMoves.add(new ChessMove(myPosition, lastRow, ChessPiece.PieceType.QUEEN));
        }
    }
}
