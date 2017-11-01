package com.chess.game;


import com.chess.bot.Bot;
import javafx.util.Pair;

import java.util.Random;
import java.util.Scanner;

public class GameManager {

    private Board board;
    private Scanner scanner;
    private Bot bot;
    private int turn;


    public GameManager(){
        this.board=new Board();
        this.scanner=new Scanner(System.in);
        this.bot=new Bot();
    }

    /**
     * Central brain, big while(true) loop, every player makes a move.
     */
    public void play(){

        this.turn=randomize();
        Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> bestMove;

        while(board.gameStatus()==0)
            if(this.turn==1) {
                this.board.move(this.turn, getUserInput("from"), getUserInput("to"));
            }
            else{
                bestMove=this.bot.chooseMove(this.board);
                this.board.move(this.turn,bestMove.getKey(),bestMove.getValue());
            }
            this.turn=changeTurn(turn);
    }

    //1==player,2==bot
    private int randomize(){
        Random rand=new Random();
        return rand.nextInt(2)+1;
    }

    //action=from/to -> remove duplicate code
    private Pair<Integer,Integer> getUserInput(String action){
        System.out.print("Player move "+action+": ");
        System.out.println();
        int input = this.scanner.nextInt();
        return new Pair<>(input /10, input %10);
    }

    private int changeTurn(int turn){
        return turn==1 ? 2 : 1;
    }
}
