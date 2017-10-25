package astar

import utils.Utils.{isValid, transition, isFinalState}

import scala.annotation.tailrec

object AStar {

  def getGraph(initialState: List[Int], pieces: Int, pegs: Int): List[Node] = {
    @tailrec
    def go(currentNode: Node, queue: List[Node], allNodes: List[Node]): List[Node] = {
      if (queue.isEmpty || isFinalState(currentNode.state))
        allNodes :+ currentNode
      else {
        //generate all possible transitions from current state
        val newStates = for {
          allPieces <- (1 to pieces).toStream
          allPegs <- (1 to pegs).toStream
          if isValid(currentNode.state, allPieces, allPegs)
        } yield transition(currentNode.state, allPieces, allPegs)

        currentNode.neighbors = newStates.toList

        val reachableNodes = newStates.toList
          .map(e => new Node(e, Some(currentNode)))
          .filterNot(n => allNodes.exists(an => an.state == n.state ||
            an.previousState.map(e => e.state) == n.previousState.map(e => e.state)))

        go(queue.head, queue.tail ::: reachableNodes, allNodes :+ currentNode)
      }
    }

    val initialNode = new Node(initialState, None)
    go(initialNode, List(initialNode), List.empty)
  }



  // create graph, with each node having a certain heuristic number
  // then dijkstra
  // distance to node and to final state distance
}
