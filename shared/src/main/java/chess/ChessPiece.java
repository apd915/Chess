package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    PieceType pieceType;
    ChessGame.TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceType = type;
        this.color = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }


    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }


    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (board.getPiece(myPosition).getPieceType()) {
            case ROOK -> {
                HashSet<ChessMove> rookMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                RookMovesHelper rook = new RookMovesHelper();

                if (row < 8) {
                    rook.up(rookMoves, board, myPosition, row, color);
                }

                if (row > 1) {
                    rook.down(rookMoves, board, myPosition, row, color);
                }

                if (col < 8) {
                    rook.right(rookMoves, board, myPosition, col, color);
                }

                if (col > 1) {
                    rook.left(rookMoves, board, myPosition, col, color);
                }

                return rookMoves;
            }

            case KNIGHT -> {
                HashSet<ChessMove> knightMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                if ((row+2 <= 8) && (col<8)){
                    row += 2;
                    col++;
                    ChessPosition upRight = new ChessPosition(row, col);
                    if ((board.getPiece(upRight) == null) || (board.getPiece(upRight).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, upRight, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row+2 <= 8) && (col>1)){
                    row += 2;
                    col--;
                    ChessPosition upLeft = new ChessPosition(row, col);
                    if ((board.getPiece(upLeft) == null) || (board.getPiece(upLeft).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, upLeft, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row-2 >= 1) && (col<8)){
                    row -= 2;
                    col++;
                    ChessPosition downRight = new ChessPosition(row, col);
                    if ((board.getPiece(downRight) == null) || (board.getPiece(downRight).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, downRight, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row-2 >= 1) && (col>1)){
                    row -= 2;
                    col--;
                    ChessPosition downLeft = new ChessPosition(row, col);
                    if ((board.getPiece(downLeft) == null) || (board.getPiece(downLeft).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, downLeft, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row<8) && (col+2 <= 8)) {
                    row++;
                    col+=2;
                    ChessPosition rightUp = new ChessPosition(row, col);
                    if ((board.getPiece(rightUp) == null) || (board.getPiece(rightUp).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, rightUp, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row>1) && (col+2 <= 8)) {
                    row--;
                    col+=2;
                    ChessPosition rightDown = new ChessPosition(row, col);
                    if ((board.getPiece(rightDown) == null) || (board.getPiece(rightDown).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, rightDown, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row<8) && (col-2 >= 1)) {
                    row++;
                    col-=2;
                    ChessPosition leftUp = new ChessPosition(row, col);
                    if ((board.getPiece(leftUp) == null) || (board.getPiece(leftUp).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, leftUp, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if ((row > 1) && (col-2>=1)) {
                    row--;
                    col-=2;
                    ChessPosition leftDown = new ChessPosition(row, col);
                    if ((board.getPiece(leftDown) == null) || (board.getPiece(leftDown).color != color)) {
                        knightMoves.add(new ChessMove(myPosition, leftDown, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                return knightMoves;
            }

            case BISHOP ->  {
                HashSet<ChessMove> bishopMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                BishopMovesHelper bishop = new BishopMovesHelper();
                if ((row < 8) && (col < 8)) {
                    bishop.upRight(bishopMoves, board, myPosition, row, col, color);
                }
                if ((row < 8) && (col > 1)) {
                    bishop.upLeft(bishopMoves, board, myPosition, row, col, color);
                }
                if ((row > 1) && (col < 8)) {
                    bishop.downRight(bishopMoves, board, myPosition, row, col, color);
                }
                if ((row > 1) && (col > 1)) {
                    bishop.downLeft(bishopMoves, board, myPosition, row, col, color);
                }
                return bishopMoves;
            }

            case QUEEN ->  {
                HashSet<ChessMove> queenMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                RookMovesHelper queenStraight = new RookMovesHelper();
                BishopMovesHelper queenDiagonal = new BishopMovesHelper();

                if (row < 8) {
                    queenStraight.up(queenMoves, board, myPosition, row, color);
                }
                if (row > 1) {
                    queenStraight.down(queenMoves, board, myPosition, row, color);
                }
                if (col < 8) {
                    queenStraight.right(queenMoves, board, myPosition, col, color);
                }
                if (col > 1) {
                    queenStraight.left(queenMoves, board, myPosition, col, color);
                }
                if ((row < 8) && (col < 8)) {
                    queenDiagonal.upRight(queenMoves, board, myPosition, row, col, color);
                }
                if ((row < 8) && (col > 1)) {
                    queenDiagonal.upLeft(queenMoves, board, myPosition, row, col, color);
                }
                if ((row > 1) && (col < 8)) {
                    queenDiagonal.downRight(queenMoves, board, myPosition, row, col, color);
                }
                if ((row > 1) && (col > 1)) {
                    queenDiagonal.downLeft(queenMoves, board, myPosition, row, col, color);
                }
                return queenMoves;
            }

            case KING ->  {
                HashSet<ChessMove> kingMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                if (row < 8) {
                    row++;
                    ChessPosition up = new ChessPosition(row, col);
                    if ((board.getPiece(up) == null) || (board.getPiece(up).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, up, null));
                    }
                    row = myPosition.getRow();
                }
                if (row > 1) {
                    row--;
                    ChessPosition down = new ChessPosition(row, col);
                    if ((board.getPiece(down) == null) || (board.getPiece(down).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, down, null));
                    }
                    row = myPosition.getRow();
                }
                if (col < 8) {
                    col++;
                    ChessPosition right = new ChessPosition(row, col);
                    if ((board.getPiece(right) == null) || (board.getPiece(right).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, right, null));
                    }
                    col = myPosition.getColumn();
                }
                if (col > 1) {
                    col--;
                    ChessPosition left = new ChessPosition(row, col);
                    if ((board.getPiece(left) == null) || (board.getPiece(left).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, left, null));
                    }
                    col = myPosition.getColumn();
                }
                if (row < 8 && col < 8) {
                    row++;
                    col++;
                    ChessPosition upRight = new ChessPosition(row, col);
                    if ((board.getPiece(upRight) == null) || (board.getPiece(upRight).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, upRight, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if (row < 8 && col > 1) {
                    row++;
                    col--;
                    ChessPosition upLeft = new ChessPosition(row, col);
                    if ((board.getPiece(upLeft) == null) || (board.getPiece(upLeft).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, upLeft, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if (row > 1 && col < 8) {
                    row--;
                    col++;
                    ChessPosition downRight = new ChessPosition(row, col);
                    if ((board.getPiece(downRight) == null) || (board.getPiece(downRight).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, downRight, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                if (row > 1 && col > 1) {
                    row--;
                    col--;
                    ChessPosition downLeft = new ChessPosition(row, col);
                    if ((board.getPiece(downLeft) == null) || (board.getPiece(downLeft).color != color)) {
                        kingMoves.add(new ChessMove(myPosition, downLeft, null));
                    }
                    row = myPosition.getRow();
                    col = myPosition.getColumn();
                }
                return kingMoves;
            }

            case PAWN ->  {
                HashSet<ChessMove> pawnMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                if (color == ChessGame.TeamColor.WHITE) {
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
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.KNIGHT));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.QUEEN));
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
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.KNIGHT));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.QUEEN));
                            }
                            if ((board.getPiece(diagonalLeft).color != color) && (row+1 != 8)) {
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, null));
                            }
                        }
                    }
                    if (row+1 == 8) {
                        ChessPosition lastRow = new ChessPosition(row+1,col);
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.ROOK));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.QUEEN));
                    }
                }
                if ((color == ChessGame.TeamColor.BLACK)) {
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
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.KNIGHT));
                                pawnMoves.add(new ChessMove(myPosition, diagonalRight, PieceType.QUEEN));
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
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.ROOK));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.BISHOP));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.KNIGHT));
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, PieceType.QUEEN));
                            }
                            if ((board.getPiece(diagonalLeft).color != color) && (row-1!=1)) {
                                pawnMoves.add(new ChessMove(myPosition, diagonalLeft, null));
                            }
                        }
                    }
                    if (row-1 == 1) {
                        ChessPosition lastRow = new ChessPosition(row-1,col);
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.ROOK));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(myPosition, lastRow, PieceType.QUEEN));
                    }

                }
                return pawnMoves;
            }

        }
        return new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceType == that.pieceType && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, color);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceType=" + pieceType +
                ", color=" + color +
                '}';
    }
}
