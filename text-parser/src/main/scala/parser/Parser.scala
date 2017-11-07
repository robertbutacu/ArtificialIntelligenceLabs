package parser

import java.io.{FileNotFoundException, IOException}

import scala.io.Source

object Parser {

  def apply(contextFilename: String,
            definitionFilename: String,
            textFilename: String,
            outputFilename: String): Unit = {
    try {
      val contextFile = Source.fromFile(contextFilename).getLines().toList
      val definitionFile = Source.fromFile(definitionFilename).getLines().toList
      val textFile = Source.fromFile(textFilename).getLines().toList
      val outputFile = Source.fromFile(outputFilename)

      val splitContext = contextFile.map(_.split("\\s+=\\s+"))
      val splitDefinitions = definitionFile.map(_.split("\\s+=\\s+"))
      val splitText = textFile.map(_.split("\\s+=\\s+"))

      var split = splitContext.map(_.flatMap(_.split(";\\s+")).toList)

      val context = split.map(e => Context(e.head, e.tail))

      split = splitDefinitions.map(_.flatMap(_.split("\\s+=\\s+")).toList)

      val definitionsNotNormalized = split.map(_.head).zip(split.map(e => e.tail.flatMap(b => b.split(";\\s+"))))

      val definitions = definitionsNotNormalized.map(d => Definition(d._1,
        d._2.map(t => (getContext(t),getDefinition(t))).toMap))

      println(definitions)

    } catch {
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException => println("Oups! Its not you, its us!")
    }
  }

  private def getContext(t: String): String = t.span(_ != ']')._1.dropWhile(c => c == ' ' || c == '[')

  private def getDefinition(t: String): String =t.span(_ != ']')._2.dropWhile(c => c == ' ' || c == ']')
}
