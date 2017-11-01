package com.chess.bot;

import com.chess.game.Board;
import javafx.util.Pair;

public class Bot {
    public Bot(){
    }

    /**
     *
     * @param board - on which the best move will be computed
     * @return - best move given a board
     */
    public Pair<Pair<Integer, Integer>,Pair<Integer,Integer>> chooseMove(Board board){
        return new Pair<>(new Pair<>(1, 1),new Pair<>(2,2));
    }

}
