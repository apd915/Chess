package server.WSHelper;

import chess.*;

import java.util.Collection;
import java.util.Objects;

public class WSMoveHelper {

    public WSMoveHelper() {}

    public boolean colorChecker(ChessPosition beginning, ChessBoard chessBoard, ChessGame.TeamColor playerColor) {
        ChessPiece chessPiece = chessBoard.getPiece(beginning);

        if (chessPiece == null ) {
            return false;
        }
        // returns true if piece is of the color of the user
        return Objects.equals(chessPiece.getTeamColor(), playerColor);
    }


}
