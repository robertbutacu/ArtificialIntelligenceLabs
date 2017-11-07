package parser

import java.io.{FileNotFoundException, IOException}

import scala.io.Source

object Parser {

  def apply(contextFilename: String,
            definitionFilename: String,
            textFilename: String,
            outputFilename: String): Unit = {
    try{
      val contextFile    = Source.fromFile(contextFilename).getLines().toList
      val definitionFile = Source.fromFile(definitionFilename).getLines().toList
      val textFile       = Source.fromFile(textFilename).getLines().toList
      val outputFile     = Source.fromFile(outputFilename)

      val splitContext = contextFile.map(_.split("\\s+=\\s+"))
      val splitDefinitions = definitionFile.map(_.split("\\s+=\\s+"))
      val splitText = textFile.map(_.split("\\s+=\\s+"))

    } catch{
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException           => println("Oups! Its not you, its us!")
    }
  }
}
