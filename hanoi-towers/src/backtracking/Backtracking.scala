package backtracking

import utils.Utils._

import scala.annotation.tailrec

object Backtracking {
  type States = List[List[Int]]

  def solveHanoi(pegs: Int, pieces: Int): List[States] = {
    def go(currentState: List[Int], currentPieceIndex: Int, previousStates: States): List[States] = {
      if (isFinalState(currentState)){
        print("FOUND")
        List(previousStates)
      }
      else {
        //println("Current state " + currentState)

        //all possible routes that could be taken from the current piece index in current state
        val validStates = (1 to currentState.head).toList.filter(e => isValid(currentState, currentPieceIndex, e))

        println("Valid states " + validStates)
        //a list of all possible states, not including previous states
        val possibleStates = validStates
          .map(p => transition(currentState, currentPieceIndex, p))
          .filterNot(previousStates.contains(_))

        println("Possible states " + possibleStates)

        println("Previous states " + previousStates)

        //Thread.sleep(3000)
        possibleStates.flatMap(s => (1 to currentState.head)
          .toList
          .filterNot(_ == currentPieceIndex)
          .flatMap(piece => go(s, piece, previousStates :+ s)))
      }
    }

    go(initialize(pegs, pieces), 1, List(List.empty))
  }
}
