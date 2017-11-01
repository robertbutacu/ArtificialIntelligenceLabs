package com.chess.bot;

import com.chess.game.Board;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
    public Bot() {
    }

    /**
     * @param board - on which the best move will be computed
     * @return - best move given a board
     */
    public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> chooseMove(Board board) {
        List<Pair<Integer, Integer>> pawns = board.retrievePawns(2);

        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> movesList = getPossibleMoves(board, pawns);

        System.out.println("Possible bot moves: ");
        movesList.stream()
                .map(x ->
                        new Pair<>(
                                new Pair<>(x.getKey().getKey() + 1,
                                        x.getKey().getValue() + 1),
                                new Pair<>(x.getValue().getKey() + 1,
                                        x.getValue().getValue() + 1)
                        ))
                .forEach((x) -> System.out.print(x + " "));
        System.out.println();

        int random = getRandomMoveIndex(movesList.size());
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> chosenMove = movesList.get(getRandomMoveIndex(movesList.size()));
        printChosenMove(chosenMove);
        return chosenMove;
    }

    private void printChosenMove(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> chosenMove) {
        System.out.println("Chosen move:");
        System.out.println((chosenMove.getKey().getKey() + 1) + "=" + (chosenMove.getKey().getValue() + 1) + "=" +
                (chosenMove.getValue().getKey() + 1) + "=" + (chosenMove.getValue().getValue() + 1));
    }

    private int getRandomMoveIndex(int listSize) {
        Random rand = new Random();
        return rand.nextInt(listSize);
    }

    private List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> getPossibleMoves(
            Board board,
            List<Pair<Integer, Integer>> pawns) {

        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> possibleMoves = new ArrayList<>();

        Pair<Integer, Integer> straightAhead;
        Pair<Integer, Integer> firstTimeMoving;
        Pair<Integer, Integer> eatingLeft;
        Pair<Integer, Integer> eatingRight;

        for (Pair<Integer, Integer> pawn : pawns) {

            straightAhead = new Pair<>(pawn.getKey() - 1, pawn.getValue());
            firstTimeMoving = new Pair<>(pawn.getKey() - 2, pawn.getValue());
            eatingLeft = new Pair<>(pawn.getKey() - 1, pawn.getValue() - 1);
            eatingRight = new Pair<>(pawn.getKey() - 1, pawn.getValue() + 1);

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

    private Boolean isValid(Pair<Integer, Integer> input) {
        return (input.getValue() >= 0 && input.getValue() <= 7)
                && (input.getKey() <= 7 && input.getKey() >= 0);
    }
}
