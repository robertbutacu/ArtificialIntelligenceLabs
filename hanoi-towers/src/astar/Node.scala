package astar

class Node(val state: List[Int],
           var previousState: List[Int],
           var neighbors: List[List[Int]],
           var distanceSoFar: Int,
           var distanceToFinal: Int = Int.MaxValue
          ) {
}
