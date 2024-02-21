package Models;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, ChessGame game) {}
