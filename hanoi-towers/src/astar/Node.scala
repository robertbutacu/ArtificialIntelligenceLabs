package astar

class Node(val state: List[Int],
           var previousState: Option[Node],
           var neighbors: List[List[Int]] = List(List.empty),
           var distanceSoFar: Int = 0,
           var distanceToFinal: Int = Int.MaxValue
          ) {
}
