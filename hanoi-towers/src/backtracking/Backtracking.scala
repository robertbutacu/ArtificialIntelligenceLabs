package backtracking

import utils.Utils._

import scala.annotation.tailrec

object Backtracking {
  type States = List[List[Int]]

  def solveHanoi(pegs: Int, pieces: Int): Unit = {
    def go(currentState: List[Int], currentPieceIndex: Int,
           previousStates: States,
           road: States): Unit = {
      if (isFinalState(currentState)){
        println("Road: " + road)
      }
      else {
        //all possible routes that could be taken from the current piece index in current state
        val validStates = (1 to currentState.head).toList.filter(e => isValid(currentState, currentPieceIndex, e))

        //a list of all possible states, not including previous states
        val possibleStates = validStates
          .map(p => transition(currentState, currentPieceIndex, p))
          .filterNot(previousStates.contains(_))

        //for each possible state, recursively call the function for each piece apart from the current one
        possibleStates.foreach(s => (1 to pieces)
          .toList
          .filterNot(_ == currentPieceIndex)
          .foreach(piece => go(s, piece, previousStates :+ s, road :+ s)))
      }
    }

    @tailrec
    def go2(states: States, road: States): States = {
      if(isFinalState(states.head))
        road :+ states.head
      else{
        //for current state, compute each possible reachable state from current position
        val newStates = states.head.drop(1).flatMap{e =>
          //for each element, try to create a new state on a different peg
          (1 to states.head.head)
            .toList
            //filtering to map only to valid transitions
            .filter(peg => isValid(states.head, e, peg))
            //transitioning to a new state
            .map( peg => transition(states.head, e, peg))
            //filtering for repetitive states
            .filterNot(states.contains)
        }
        println(newStates)
        go2(states.drop(1) ::: newStates, road :+ states.head)
      }
    }


    go2(List(initialize(pegs, pieces)), List(List.empty))
    //go(initialize(pegs, pieces), 1, List(List.empty), List())
  }
}
