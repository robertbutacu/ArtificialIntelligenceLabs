import astar.AStar._
import backtracking.Backtracking._
import utils.{All, First, Utils}

object Main extends App{
  val nodes = getGraph(Utils.initialize(4,3), 3, 4)

  //nodes.foreach(n => println(n.state + " neighbors " + n.previousState.map(e => e.state)))
  //println(nodes.length)

  updateDistancesToFinal(nodes)
  //nodes.foreach(n => println(n.state + " " + n.distanceToFinal))

  val shortestDistance = getShortestDistance(nodes)

  shortestDistance.foreach(n => println(n.state))
}
