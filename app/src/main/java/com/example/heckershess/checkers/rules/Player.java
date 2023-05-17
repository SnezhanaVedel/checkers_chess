package com.example.heckershess.checkers.rules;

import com.example.heckershess.checkers.board.BoardCell;

public enum Player {
    WHITE,
    BLACK;

    public Player getOpposite() {
        return (this == WHITE) ? BLACK : WHITE;
    }

    public int getPieceColor() {
        return (this == WHITE) ? BoardCell.WHITE_PIECE : BoardCell.BLACK_PIECE;
    }

    public String getPlayerName() {
        return (this == WHITE) ? "Белы" : "Чёрны";
    }
}
