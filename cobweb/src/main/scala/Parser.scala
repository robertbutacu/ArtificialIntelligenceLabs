import scala.io.Source

object Parser {
  def parse(inputFile: String): List[GeoNames] = {
    val file = Source.fromFile(inputFile)

    val lines = file.getLines().toList

    lines.map(l => l.split("\\s+").toList).foreach(println)

    List.empty
  }

}
