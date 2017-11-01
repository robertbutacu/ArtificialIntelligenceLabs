package com.chess.game;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.max;

public class Board {
    private int[][] board;

    Board() {
        board = new int[8][8];
        initializeBoard();
    }

    public List<Pair<Integer, Integer>> retrievePawns(int player){
        List<Pair<Integer, Integer>> playerPawns = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == player)
                    playerPawns.add(new Pair<>(i, j));
            }
        }

        return playerPawns;
    }

    /**
     * Conditions to be met to win/draw a game:
     * 1. reached the end of the board
     * 2. no pieces left of the enemy
     * 3. all pawns are facing each other, with no other possible move
     *
     * @return 0 => game still going, 1 => player, 2 => bot, or the other way around, 4 => draw
     */
    int gameStatus() {
        return max(max(playerWins(), botWins()), isDraw());
    }

    private int playerWins() {
        return max((Arrays.stream(board[7]).sum() > 0 ? 1 : 0), noEnemyPiecesLeft(2));
    }

    private int botWins() {
        return max((Arrays.stream(board[0]).sum() > 0 ? 2 : 0), noEnemyPiecesLeft(1));
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
                    if (!columnBlockedByOpponentPiece(new Pair<>(i, j)) || canEatOpponentPiece(new Pair<>(i, j)))
                        return 0;
        return 4;
    }

    private boolean columnBlockedByOpponentPiece(Pair<Integer,Integer> coordinates) {
        return board[coordinates.getKey() + 1][coordinates.getValue()] != 0;
    }

    private boolean canEatOpponentPiece(Pair<Integer,Integer> coordinates) {
        return (board[coordinates.getKey() + 1][coordinates.getValue() - 1] != 0) ||
                (board[coordinates.getKey() + 1][coordinates.getValue() + 1] != 0);
    }

    /**
     *
     * @param player - bot or human
     * @param from - starting point of the piece
     * @param to - end point of the piece
     * @return - true if the piece has been moved, false otherwise
     */
    Boolean move(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        if (isValidPlayerTurn(player) && isPlayerPiece(player, from)) {
            return movePiece(player, from, to);
        } else return false;
    }

    private Boolean isValidPlayerTurn(int player) {
        return player == 1 || player == 2;
    }

    private Boolean isPlayerPiece(int player, Pair<Integer, Integer> coordinates) {
        return board[coordinates.getKey()][coordinates.getValue()] == player;
    }

    private Boolean movePiece(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        if (isValidMove(player, from, to)) {
            board[from.getKey()][from.getValue()] = 0;
            board[to.getKey()][to.getValue()] = player;
            return true;
        } else return false;
    }

    /**
     * It is a valid move in the situations:
     *  1. moves 1 box ahead and there is an empty spot
     *  2. starting point -> can move 1 or 2 pieces ahead
     *  3. diagonally 1 box, in order to eat an enemy piece
     *
     * @param player - 1 or 2
     * @param from - starting point
     * @param to - end position
     * @return true if its valid move, false otherwise
     */
    public Boolean isValidMove(int player, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        // one player starts from 6th row, coming down to 0, the other from 1st row, going up to 7th
        int forward         = player == 1 ? 1 : -1;
        int firstPieceIndex = player == 1 ? 1 : 6;
        int enemy           = player == 1 ? 2 : 1;

        return isFirstTimeMovingPiece(firstPieceIndex, from, to, forward)
                || isMovingCorrectly(enemy, from, to, forward);
    }

    /**
     * Key => row
     * Value => column
     */
    private Boolean isFirstTimeMovingPiece(int firstPieceIndex,
                                           Pair<Integer, Integer> from,
                                           Pair<Integer, Integer> to,
                                           int forward) {
        //moving 2 boxes straight => valid cause its first piece
        return from.getValue() == firstPieceIndex
                && to.getValue().intValue() == from.getValue().intValue()
                && to.getKey() == (from.getKey() + 2 * forward)
                && board[to.getKey()][to.getValue()] == 0;
    }

    private Boolean isMovingCorrectly(int enemy,
                                      Pair<Integer, Integer> from,
                                      Pair<Integer, Integer> to,
                                      int forward) {
        //moving 1 box straight
        if (to.getValue().intValue() == from.getValue().intValue()
                && to.getKey() == (from.getKey() + forward)
                && board[to.getKey()][to.getValue()] == 0)
            return true;


        //moving 1 box to the right, eating an opposing piece
        if (to.getValue() == (from.getValue() + 1)
                && to.getKey() == (from.getKey() + forward)
                && board[to.getKey()][to.getValue()] == enemy)
            return true;


        //moving 1 box to the left, eating an opposing piece
        return to.getValue() == (from.getValue() - 1)
                && to.getKey() == (from.getKey() + forward)
                && board[to.getKey()][to.getValue()] == enemy;
    }


    private void initializeBoard() {
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
