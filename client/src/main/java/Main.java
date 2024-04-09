import ResponseException.ResponseException;
import chess.*;
import ui.PreLogin;

public class Main {

    public static void main(String[] args) throws ResponseException {
        System.out.println("Cheese");
        PreLogin preLogin = new PreLogin();

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);


        boolean loop = true;
        while (loop) {
            loop = preLogin.determineState();
        }

    }
}