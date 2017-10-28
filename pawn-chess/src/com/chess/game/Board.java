package com.chess.game;

import javafx.util.Pair;

public class Board {
    private int [][] board;

    public Board() {
        board = new int[8][8];
        initiateBoard();
    }

    public void move(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to){
        if(isValidPlayerTurn(player) && isPlayerPiece(player, from)){
            board[from.getKey()][from.getValue()] = 0;
            board[to.getKey()][to.getValue()] = player;
        }
    }

    private Boolean isValidPlayerTurn(int player){
        return player == 1 || player == 2;
    }

    private Boolean isPlayerPiece(int player, Pair<Integer, Integer> coordinates){
        return board[coordinates.getKey()][coordinates.getValue()] == player;
    }

    private void initiateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(i == 1)
                    board[i][j] = 1;
                else if(i == 6)
                    board[i][j] = 2;
                else
                    board[i][j] = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder boardView = new StringBuilder();

        boardView.append(" \t");

        for (int i = 0; i < 8; i++) {
            boardView.append(i).append(" ");
        }

        boardView.append("\n\n");

        for (int i = 0; i < 8; i++) {
            boardView.append(i).append("\t");
            for (int j = 0; j < 8; j++) {
                boardView.append(board[i][j]);
                boardView.append(" ");
            }

            boardView.append("\n");
        }

        return boardView.toString();
    }
}
