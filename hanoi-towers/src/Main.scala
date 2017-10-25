import astar.AStar._
import backtracking.Backtracking._
import utils.{All, First, Utils}

object Main extends App{
  val nodes = getGraph(Utils.initialize(3,3), 3, 3)

  nodes.foreach(n => println(n.state + " previous " + n.previousState.map(e => e.state)))
  println(nodes.length)
}
