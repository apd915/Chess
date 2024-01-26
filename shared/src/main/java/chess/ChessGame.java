package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board;
    TeamColor currentTeam;

    public ChessGame() {
        this.board = new ChessBoard();
        this.currentTeam = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currentTeam = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        switch (piece.getPieceType()) {
            case ROOK -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.ROOK).pieceMoves(board, startPosition);
            }
            case BISHOP -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.BISHOP).pieceMoves(board, startPosition);
            }
            case QUEEN -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.QUEEN).pieceMoves(board, startPosition);
            }
            case KNIGHT -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.KNIGHT).pieceMoves(board, startPosition);
            }
            case KING -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.KING).pieceMoves(board, startPosition);
            }
            case PAWN -> {
                return new ChessPiece(currentTeam, ChessPiece.PieceType.PAWN).pieceMoves(board, startPosition);
            }
        }
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // switch team color at the end
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opposingTeam = opposingTeam(teamColor);

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                ChessPosition currentPosition = new ChessPosition(row, col);
                if (currentPiece != null) {
                    if (currentPiece.color == opposingTeam) {
                        Collection<ChessMove> moves = new ChessPiece(opposingTeam, currentPiece.getPieceType()).pieceMoves(board, currentPosition);
                        for (ChessMove move : moves) {
                            ChessPiece pieceAtPos = board.getPiece(move.getEndPosition());
                            if (pieceAtPos != null) {
                                if (pieceAtPos.getPieceType() == ChessPiece.PieceType.KING) {
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }
    /**
     * Helper function for isInCheck
     */
    public TeamColor opposingTeam(TeamColor teamColor) {
        switch (teamColor) {
            case BLACK -> {
                return TeamColor.WHITE;
            }
            case WHITE -> {
                return TeamColor.BLACK;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board)   {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
