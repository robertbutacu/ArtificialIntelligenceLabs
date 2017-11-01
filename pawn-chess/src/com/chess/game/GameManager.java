package com.chess.game;


import javafx.util.Pair;

import java.util.Random;
import java.util.Scanner;

public class GameManager {

    private Board board;
    Scanner scanner;
    int input;

    public GameManager(){
        this.board=new Board();
        this.scanner=new Scanner(System.in);
    }

    /**
     * Central brain, big while(true) loop, every player makes a move.
     */
    public void play(){
//        int turn=randomize();
//        while(board.gameStatus()==0)
//            if(turn==1) {
//                this.board.move(turn,);
//            }
        //am facut ceva dimineata..nu mai am timp sa termin


    }

    //1==player,2==bot
    private int randomize(){
        Random rand=new Random();
        return rand.nextInt(2)+1;
    }

    private int changeTurn(int turn){
       return turn==1 ? 2 : 1;
    }

    private Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> getUserInput(){
        System.out.print("Player move from: ");
        System.out.println();
        input=this.scanner.nextInt();
        Pair<Integer,Integer>from = new Pair<>(input/10,input%10);
        System.out.print("Player move to: ");
        System.out.println();
        input=this.scanner.nextInt();
        Pair<Integer,Integer>to = new Pair<>(input/10,input%10);
        return new Pair<>(from,to);
    }
}
