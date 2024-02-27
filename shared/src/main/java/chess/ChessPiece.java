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
                KnightMovesHelper knight = new KnightMovesHelper();

                if ((row+2 <= 8) && (col<8)){
                    knight.upRight(knightMoves, board, myPosition, row, col, color);
                }
                if ((row+2 <= 8) && (col>1)){
                    knight.upLeft(knightMoves, board, myPosition, row, col, color);
                }
                if ((row-2 >= 1) && (col<8)){
                    knight.downRight(knightMoves, board, myPosition, row, col, color);
                }
                if ((row-2 >= 1) && (col>1)) {
                    knight.downLeft(knightMoves, board, myPosition, row, col, color);
                }
                if ((row<8) && (col+2 <= 8)) {
                    knight.rightUp(knightMoves, board, myPosition, row, col, color);
                }
                if ((row>1) && (col+2 <= 8)) {
                    knight.rightDown(knightMoves, board, myPosition, row, col, color);
                }
                if ((row<8) && (col-2 >= 1)) {
                    knight.leftUp(knightMoves, board, myPosition, row, col, color);
                }
                if ((row > 1) && (col-2>=1)) {
                    knight.leftDown(knightMoves, board, myPosition, row, col, color);
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
                PawnKingHelper king = new PawnKingHelper();

                if (row < 8) {
                    king.kingUp(kingMoves, board, myPosition, row, col, color);
                }
                if (row > 1) {
                    king.kingDown(kingMoves, board, myPosition, row, col, color);
                }
                if (col < 8) {
                    king.kingRight(kingMoves, board, myPosition, row, col, color);
                }
                if (col > 1) {
                    king.kingLeft(kingMoves, board, myPosition, row, col, color);
                }
                if (row < 8 && col < 8) {
                    king.kingUpRight(kingMoves, board, myPosition, row, col, color);
                }
                if (row < 8 && col > 1) {
                    king.kingUpLeft(kingMoves, board, myPosition, row, col, color);
                }
                if (row > 1 && col < 8) {
                    king.kingDownRight(kingMoves, board, myPosition, row, col, color);
                }
                if (row > 1 && col > 1) {
                    king.kingDownLeft(kingMoves, board, myPosition, row, col, color);
                }
                return kingMoves;
            }

            case PAWN ->  {
                HashSet<ChessMove> pawnMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                PawnKingHelper pawn = new PawnKingHelper();

                if (color == ChessGame.TeamColor.WHITE) {
                    pawn.pawnWhite(pawnMoves, board, myPosition, row, col, ChessGame.TeamColor.WHITE);
                }
                if ((color == ChessGame.TeamColor.BLACK)) {
                    pawn.pawnBlack(pawnMoves, board, myPosition, row, col, ChessGame.TeamColor.BLACK);
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
