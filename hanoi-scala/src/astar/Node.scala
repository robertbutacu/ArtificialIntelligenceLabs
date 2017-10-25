package astar

class Node(val state: List[Int],
           var previousState: Option[Node],
           var neighbors: List[Node] = List.empty,
           var distanceSoFar: Int = 0,
           var distanceToFinal: Int = Int.MaxValue
          ) {
  override def toString: String = this.state.toString()
}
