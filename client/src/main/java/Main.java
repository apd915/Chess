import chess.*;
import ui.DrawBoard;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        DrawBoard board = new DrawBoard();

//        ChessBoard board = new ChessBoard();
//        board.resetBoard();
//        DrawBoard board = new DrawBoard(board);
    }
}