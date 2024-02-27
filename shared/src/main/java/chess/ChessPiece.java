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
                rook.moves(rookMoves, board, myPosition, row, col, color);
                return rookMoves;
            }

            case KNIGHT -> {
                HashSet<ChessMove> knightMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                KnightMovesHelper knight = new KnightMovesHelper();
                knight.moves(knightMoves, board, myPosition, row, col, color);
                return knightMoves;
            }

            case BISHOP ->  {
                HashSet<ChessMove> bishopMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                BishopMovesHelper bishop = new BishopMovesHelper();
                bishop.moves(bishopMoves, board, myPosition, row, col, color);
                return bishopMoves;
            }

            case QUEEN ->  {
                HashSet<ChessMove> queenMoves = new HashSet<>();
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                RookMovesHelper queenStraight = new RookMovesHelper();
                BishopMovesHelper queenDiagonal = new BishopMovesHelper();
                queenStraight.moves(queenMoves, board, myPosition, row, col, color);
                queenDiagonal.moves(queenMoves, board, myPosition, row, col, color);
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
