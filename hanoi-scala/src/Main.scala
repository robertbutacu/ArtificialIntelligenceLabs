import astar.AStar._
import backtracking.Backtracking._
import utils.{All, First, Utils}

object Main extends App{
  def time[R](block: => R, name: String): R = {
    val t0 = System.currentTimeMillis()
    val result = block // call-by-name
    val t1 = System.currentTimeMillis()
    println(s"Elapsed time on $name: " + (t1 - t0) + "ms")
    result
  }

  def aStarDemo(): Unit = {
    val nodes = getGraph(Utils.initialize(3,3), 3, 3)

    updateDistancesToFinal(nodes)

    val shortestDistance = getShortestDistance(nodes)

    println("Number of steps to solution: " + shortestDistance._1)
  }

  def backtrackingDemo(): Unit = {
    println("Number of steps to solution: " + solveHanoi(3, 3, First))
  }

  println(time(aStarDemo(), "A*"))
  println(time(backtrackingDemo(), "backtracking"))
}
