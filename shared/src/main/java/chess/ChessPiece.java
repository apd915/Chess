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
            case ROOK ->
            {   HashSet<ChessMove> rookMoves = new HashSet<>();

                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                if (row < 8) {
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

                if (row > 1) {
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

                if (col < 8) {
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

                if (col > 1) {
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

                for (ChessMove chessMove: rookMoves) {
                    System.out.println(chessMove);
                }
                return rookMoves;
            }

//            case KNIGHT -> {
//                return true;
//            }
//
            case BISHOP ->  {
                HashSet<ChessMove> bishopMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                if ((row+1 < 8) && (col+1 < 8)) {
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


                if ((row+1 < 8) && (col-1 > 1)) {
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

                if ((row-1 > 1) && (col+1 < 8)) {
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

                if ((row-1 > 1) && (col-1 > 1)) {
                    row--;
                    col--;
                    ChessPosition downLeft = new ChessPosition(row, col);
                    while ((row > 1) && (col > 1) && (board.getPiece(downLeft) == null)) {
                        bishopMoves.add(new ChessMove(myPosition, downLeft, null));
                        row--;
                        col++;
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
                for (ChessMove chessMove: bishopMoves) {
                    System.out.println(chessMove);
                }
                return bishopMoves;
            }
//
//            case QUEEN ->  {
//                return true;
//            }
//
//            case KING ->  {
//                return true;
//            }
//
//            case PAWN ->  {
//                return true;
//            }

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
