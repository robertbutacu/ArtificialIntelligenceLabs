package backtracking

import utils.{All, BacktrackingMethod, First}
import utils.Utils._

import scala.annotation.tailrec

object Backtracking {
  type States = List[List[Int]]

  def solveHanoi(pegs: Int, pieces: Int, method: BacktrackingMethod): Either[Int, Unit] = {
    def go(currentState: List[Int], currentPieceIndex: Int,
           previousStates: States,
           road: States): Unit = {
      if (isFinalState(currentState)) {
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
    def go2(states: List[List[Int]], visited: Set[List[Int]], road: States): Int = {
      if (isFinalState(states.head)) {
        (road :+ states.head).filterNot(_.isEmpty).length
      }
      else {
        val currentState = states.head

        //for current state, compute each possible reachable state from current position
        val newStates = (1 to pieces).toList.flatMap{ pieceIndex =>
          //for each element, try to create a new state on a different peg
          (1 to currentState.head)
            .toList
            //filtering to map only to valid transitions
            .filter(peg => isValid(currentState, pieceIndex, peg))
            //transitioning to a new state PIECE INDEX
            .map(peg => transition(currentState, pieceIndex, peg))
            //filtering for repetitive states which will lead to cycles
            .filterNot(states.contains)
        }
        go2(states.drop(1) ++ newStates.drop(1), visited + states.head, road :+ states.head)
      }
    }

    method match {
      case First => Left(go2(List(initialize(pegs, pieces)), Set.empty, List(List.empty)))
      case All   => Right(go(initialize(pegs, pieces), 1, List(List.empty), List()))

    }
  }
}
