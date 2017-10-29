package com.chess.game;

import javafx.util.Pair;

import java.util.Arrays;

import static java.lang.Integer.max;

public class Board {
    private int[][] board;

    public Board() {
        board = new int[8][8];
        initiateBoard();
    }

    /**
     * Conditions to be met to win/draw a game:
     * 1. reached the end of the board
     * 2. no pieces left of the enemy
     * 3. all pawns are facing each other, with no other possible move
     *
     * @return 0 => game still going, 1 => player, 2 => bot, or the other way around, 4 => draw
     */
    public int gameStatus() {
        return max(max(playerWins(), botWins()), isDraw());
    }

    private int playerWins() {
        return max((Arrays.stream(board[0]).sum() > 0 ? 1 : 0), noEnemyPiecesLeft(2));
    }

    private int botWins() {
        return max((Arrays.stream(board[7]).sum() > 0 ? 2 : 0), noEnemyPiecesLeft(1));
    }

    private int noEnemyPiecesLeft(int enemy) {
        for (int i = 1; i < board.length - 1; i++)
            if (Arrays.stream(board[i]).anyMatch(piece -> piece == enemy))
                return 0;
        return enemy == 1 ? 2 : 1;
    }

    private int isDraw() {
        for (int i = 1; i < board.length - 1; i++)
            for (int j = 1; j < board.length - 1; j++)
                if (board[i][j] == 1)
                    if (!columnBlockedByOpponentPiece(i, j) || canEatOpponentPiece(i, j))
                        return 0;
        return 4;
    }

    private boolean columnBlockedByOpponentPiece(int row, int column) {
        return board[row + 1][column] != 0;
    }

    private boolean canEatOpponentPiece(int row, int column) {
        return (board[row + 1][column - 1] != 0) || (board[row + 1][column + 1] != 0);
    }

    public void move(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        if (isValidPlayerTurn(player) && isPlayerPiece(player, from)) {
            movePiece(player, from, to);
        }
    }

    private void movePiece(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        board[from.getKey()][from.getValue()] = 0;
        board[to.getKey()][to.getValue()] = player;
    }

    /**
     * There are  possible moves:
     * <p>
     * 1. moves for the first time a piece => means to._1 - from._1 <= 2
     * 2. piece already has been moved: to._1 - from._1 <= 1
     * 3. there is a piece of the opponent in either to._2 + 1 or to._2 - 1
     */

    private Boolean isValidMove(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        return true;
    }

    private Boolean isValidPlayerTurn(int player) {
        return player == 1 || player == 2;
    }

    private Boolean isPlayerPiece(int player, Pair<Integer, Integer> coordinates) {
        return board[coordinates.getKey()][coordinates.getValue()] == player;
    }

    private void initiateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1)
                    board[i][j] = 1;
                else if (i == 6)
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
