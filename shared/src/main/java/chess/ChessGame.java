package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
            // Based on the piece, we determine which piece to implement
            case ROOK -> {
                return determineValid(startPosition, piece);
            }
            case BISHOP -> {
                return determineValid(startPosition, piece);
            }
            case QUEEN -> {
                return determineValid(startPosition, piece);
            }
            case KNIGHT -> {
                return determineValid(startPosition, piece);
            }
            case KING -> {
                return determineValid(startPosition, piece);
            }
            case PAWN -> {
                return determineValid(startPosition, piece);
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
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece pieceToMove = board.getPiece(startPosition);
        TeamColor teamColor = pieceToMove.getTeamColor();
        Collection<ChessMove> valid = validMoves(startPosition);

        if (pieceToMove == null) {
            throw new InvalidMoveException("Invalid Move");
        }
        if (board.getPiece(startPosition).getTeamColor() != currentTeam) {
            // can't use move piece if it's opponents
            throw new InvalidMoveException("Invalid Move");
        }

        if (valid.contains(move)) {
            // runs if the move is part of the valid list
            if (move.getPromotionPiece() != null) {
                // path if there is a piece at given position
                switch (move.getPromotionPiece()) {
                    case ROOK -> {
                        promotePiece(startPosition, endPosition, teamColor,ChessPiece.PieceType.ROOK);
                    }
                    case KNIGHT -> {
                        promotePiece(startPosition, endPosition, teamColor,ChessPiece.PieceType.KNIGHT);
                    }
                    case BISHOP -> {
                        promotePiece(startPosition, endPosition, teamColor,ChessPiece.PieceType.BISHOP);
                    }
                    case QUEEN -> {
                        promotePiece(startPosition, endPosition, teamColor,ChessPiece.PieceType.QUEEN);
                    }
                }
            } else {
                board.addPiece(endPosition, pieceToMove);
                board.addPiece(startPosition, null);
                currentTeam = (currentTeam == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
            }
        }
        else {
            throw new InvalidMoveException("Invalid Move");
        }
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
                // Iterate through rows and columns get a piece and its position
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                ChessPosition currentPosition = new ChessPosition(row, col);
                if (currentPiece != null) {
                    if (currentPiece.color == opposingTeam) {
                        // if there is a piece at position of the opposing color, get its possible moves
                        Collection<ChessMove> moves = new ChessPiece(opposingTeam, currentPiece.getPieceType()).pieceMoves(board, currentPosition);
                        for (ChessMove move : moves) {
                            // Check its positions at the move
                            ChessPiece pieceAtPos = board.getPiece(move.getEndPosition());
                            if (pieceAtPos != null) {
                                // if position is not empty, check whether it is a King
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
        // find king, get its moves check its positions
        ChessPosition kingPosition = findPiece(ChessPiece.PieceType.KING, teamColor);
        ChessPiece king = board.getPiece(kingPosition);
        Collection<ChessMove> kingMoves = king.pieceMoves(board, kingPosition);
        checkmateHelper(kingPosition, king, kingMoves);
        return (isInCheck(teamColor) && checkmateHelper(kingPosition, king, kingMoves));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // get the king, find the opposing team and make a list of their moves
        ChessPosition kingPosition = findPiece(ChessPiece.PieceType.KING, teamColor);
        ChessPiece king = board.getPiece(kingPosition);
        Collection<ChessMove> kingMoves = king.pieceMoves(board, kingPosition);

        Collection<ChessPosition> opposingTeam = findTeam(opposingTeam(teamColor));
        Collection<Collection<ChessMove>> opposingMoves = teamMoves(opposingTeam);

        int dangerArea = 0;

        for (ChessMove kingMove : kingMoves) {
            // for every possible move of the king, compare it with each move of its opponents
            int kingRow = kingMove.getEndPosition().getRow();
            int kingCol = kingMove.getEndPosition().getColumn();
            for (Collection<ChessMove> piece : opposingMoves) {
                for (ChessMove move : piece) {
                    int enemyRow = move.getEndPosition().getRow();
                    int enemyCol = move.getEndPosition().getColumn();
                    if ((enemyRow == kingRow) && (enemyCol == kingCol)) {
                        // if a position matches perfectly, increase a counter
                        dangerArea++;
                    }
                }
            }
        }
        // if counter matches or exceeds, it means King is in stalemate
        return (dangerArea >= kingMoves.size());
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

    private ChessPosition findPiece(ChessPiece.PieceType piece, TeamColor teamColor) {
        // Look for a specific piece and its color
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece currentPiece = board.getPiece(currentPosition);
                if (currentPiece != null && currentPiece.getPieceType() == piece && currentPiece.getTeamColor() == teamColor) {
                    return currentPosition;
                }
            }
        }
        return null;
    }

    private Collection<ChessPosition> findTeam(TeamColor color) {
        // Look for the positions of a whole team of a given color
        // Return a list of these positions
        HashSet<ChessPosition> team = new HashSet<>();
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                ChessPosition currentPosition = new ChessPosition(row, col);
                if (currentPiece != null) {
                    if (currentPiece.color == color) {
                        team.add(currentPosition);
                    }
                }
            }
        }
        return team;
    }

    private Collection<Collection<ChessMove>> teamMoves(Collection<ChessPosition> piecePositions) {
        HashSet<Collection<ChessMove>> teamMoves = new HashSet<>();

        for (ChessPosition position : piecePositions) {
            ChessPiece piece = board.getPiece(position);
            Collection<ChessMove> moves = piece.pieceMoves(board, position);
            teamMoves.add(moves);
        }

        return teamMoves;
    }

    private boolean checkmateHelper(ChessPosition kingPosition, ChessPiece king, Collection<ChessMove> kingMoves) {
        //Go through each move of the King, see if at that position it would hypothetically be in check,
        // if true, increase counter
        // if death counter matches moves of King, it means he's threatened from all positions
        int death = 0;
        for (ChessMove move : kingMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                ChessPiece piece = board.getPiece(move.getEndPosition());
                board.addPiece(move.getEndPosition(), king);
                board.addPiece(kingPosition, null);
                if (isInCheck(king.getTeamColor())) {
                    death++;
                }
                board.addPiece(kingPosition, king);
                board.addPiece(move.getEndPosition(), piece);
            }
            else {
                board.addPiece(move.getEndPosition(), king);
                board.addPiece(kingPosition, null);
                if (isInCheck(king.getTeamColor())) {
                    death++;
                }
                board.addPiece(kingPosition, king);
                board.addPiece(move.getEndPosition(), null);
            }

        }

        return (death == kingMoves.size());
    }

    private Collection<ChessMove> determineValid(ChessPosition startPosition,ChessPiece piece) {
        // Get a piece and a start position, retrieve moves of piece at that position, initialize a list
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, startPosition);
        TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new HashSet<>();

        for (ChessMove move : pieceMoves) {
            //Go through each move, check if it would hypothetically cause a Check
            // Two scenarios, one where endPosition is occupied and one where it is open
            ChessPosition endPosition = move.getEndPosition();
            if (board.getPiece(endPosition) != null) {
                //endPosition is occupied
                ChessPiece opposingPiece = board.getPiece(endPosition);
                board.addPiece(move.getEndPosition(), piece);
                board.addPiece(startPosition, null);
                if (!isInCheck(teamColor)) {
                    //if it doesn't cause check, add it to validMoves
                    validMoves.add(move);
                }
                board.addPiece(startPosition, piece);
                board.addPiece(endPosition, opposingPiece);
            } else  {
                //endPosition is open
                board.addPiece(endPosition, piece);
                board.addPiece(startPosition, null);
                if (!isInCheck(teamColor)) {
                    validMoves.add(move);
                }
                board.addPiece(startPosition, piece);
                board.addPiece(endPosition, null);
            }
        }
        return validMoves;
    }


    private void promotePiece(ChessPosition startPosition, ChessPosition endPosition, TeamColor teamColor,ChessPiece.PieceType promoteTo) {
        //Takes into account what would happen if a pawn gets to the end and an input is given to what piece to promote to
        //Change team color at the end as turn ends.
        board.addPiece(endPosition, new ChessPiece(teamColor, promoteTo));
        board.addPiece(startPosition, null);
        currentTeam = (currentTeam == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
    }

}
