package parser

import java.io.{FileNotFoundException, IOException}

import scala.io.Source

object Parser {

  def apply(contextFilename: String,
            definitionFilename: String,
            textFilename: String,
            outputFilename: String): Unit = {
    try{
      val contextFile    = Source.fromFile(contextFilename)
      val definitionFile = Source.fromFile(definitionFilename)
      val textFile       = Source.fromFile(textFilename)
      val outputFile     = Source.fromFile(outputFilename)

      println(contextFile.getLines().toList)
      println(definitionFile.getLines().toList)
      println(textFile.getLines().toList)
      println(outputFile.getLines().toList)
    } catch{
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException           => println("Oups! Its not you, its us!")
    }
  }
}
