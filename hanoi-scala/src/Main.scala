import astar.AStar._
import backtracking.Backtracking._
import utils.{All, First, Utils}

object Main extends App{

  def aStarDemo(): Unit = {
    val nodes = getGraph(Utils.initialize(3,3), 3, 3)

    //nodes.foreach(n => println(n.state + " neighbors " + n.previousState.map(e => e.state)))
    //println(nodes.length)

    updateDistancesToFinal(nodes)
    //nodes.foreach(n => println(n.state + " " + n.distanceToFinal))

    val shortestDistance = getShortestDistance(nodes)

    shortestDistance.foreach(n => println(n.state))
  }

  def backtrackingDemo(): Unit = {
    println(solveHanoi(3, 3, First))
  }

  aStarDemo()
  backtrackingDemo()
}
