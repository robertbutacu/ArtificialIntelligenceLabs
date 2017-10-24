package backtracking

import utils.Utils._

object Backtracking {
  type States = List[List[Int]]

  def solveHanoi(pegs: Int, pieces: Int): Unit = {
    def go(currentState: List[Int], currentPieceIndex: Int,
           previousStates: States,
           road: States): Unit = {
      if (isFinalState(currentState)){
        println(road)
      }
      else {
        //all possible routes that could be taken from the current piece index in current state
        val validStates = (1 to currentState.head).toList.filter(e => isValid(currentState, currentPieceIndex, e))

        //a list of all possible states, not including previous states
        val possibleStates = validStates
          .map(p => transition(currentState, currentPieceIndex, p))
          .filterNot(previousStates.contains(_))

        //for each possible state, recursively call the function for each piece apart from the current one
        possibleStates.foreach(s => (1 to currentState.head)
          .toList
          .filterNot(_ == currentPieceIndex)
          .foreach(piece => go(s, piece, previousStates :+ s, road :+ s)))
      }
    }

    go(initialize(pegs, pieces), 1, List(List.empty), List())
  }
}
