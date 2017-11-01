package com.chess.bot;

import com.chess.game.Board;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Bot {
    public Bot() {
    }

    /**
     * @param board - on which the best move will be computed
     * @return - best move given a board
     */
    public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> chooseMove(Board board) {
        List<Pair<Integer, Integer>> pawns = board.retrievePawns(2);

        System.out.println(getPossibleMoves(board, pawns));

        return new Pair<>(new Pair<>(1, 1), new Pair<>(2, 2));
    }

    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getPossibleMoves(
            Board board,
            List<Pair<Integer, Integer>> pawns) {
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> possibleMoves = new ArrayList<>();

        for (Pair<Integer, Integer> pawn : pawns) {
            Pair<Integer, Integer> straightAhead = new Pair<>(pawn.getKey(), pawn.getValue() + 1);

            Pair<Integer, Integer> firstTimeMoving = new Pair<>(pawn.getKey(), pawn.getValue() + 2);
            Pair<Integer, Integer> eatingLeft = new Pair<>(pawn.getKey() - 1, pawn.getValue() + 1);
            Pair<Integer, Integer> eatingRight = new Pair<>(pawn.getKey() + 1, pawn.getValue() + 1);

            if (isValid(straightAhead) && board.isValidMove(2, pawn, straightAhead))
                possibleMoves.add(new Pair<>(pawn, straightAhead));

            if (isValid(firstTimeMoving) && board.isValidMove(2, pawn, firstTimeMoving))
                possibleMoves.add(new Pair<>(pawn, firstTimeMoving));

            if (isValid(eatingLeft) && board.isValidMove(2, pawn, eatingLeft))
                possibleMoves.add(new Pair<>(pawn, eatingLeft));

            if (isValid(eatingRight) && board.isValidMove(2, pawn, eatingRight))
                possibleMoves.add(new Pair<>(pawn, eatingRight));

        }

        return possibleMoves;
    }

    private Boolean isValid(Pair<Integer, Integer> input){
        return (input.getValue() >= 0 && input.getValue() <= 7)
                && (input.getKey() <= 7 && input.getKey() >= 0);
    }
}
