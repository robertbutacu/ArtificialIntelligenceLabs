package parser

import java.io.{File, FileNotFoundException, IOException, PrintWriter}

import scala.io.Source
import parser.SentenceAnalyzer._

object Parser {

  def apply(contextFilename: String,
            definitionFilename: String,
            textFilename: String,
            outputFilename: String): Unit = {
    try {
      val contextFile = Source.fromFile(contextFilename).getLines().toList
      val definitionFile = Source.fromFile(definitionFilename).getLines().toList
      val textFile = Source.fromFile(textFilename).getLines().toList
      //Scala standard library doesn’t contain any classes to write files
      val outputFile = new PrintWriter(new File(outputFilename))

      //splitting every single line into a list of strings ( actually, it will only be a tuple), where the head
      // is the word/context and the rest, a list of definitions/contexts.

      //using span is not an option , since it compares every char individually
      val splitContext = contextFile.map(_.split("\\s+=\\s+"))
      val splitDefinitions = definitionFile.map(_.split("\\s+=\\s+"))

      //currently, there is a list of 2 lists: context def and the contexts
      //in order to obtain a list of various contexts, it is used the split functions, knowing
      // as they are delimited by "; ".
      var split = splitContext.map(_.flatMap(_.split(";\\s+")).toList)

      //mapping to a list of Context
      val context = split.map(e => Context(e.head, e.tail))

      //idk why im doing this
      //TODO figure out what's happening here
      split = splitDefinitions.map(_.flatMap(_.split("\\s+=\\s+")).toList)

      //a list of tuples represented by (tag, list(definitions))
      val definitionsNotNormalized = split.map(_.head).zip(split.map(e => e.tail.flatMap(b => b.split(";\\s+"))))

      //mapping definitions to actual objects so it will be easier to work with
      val definitions = definitionsNotNormalized.map(d => Definition(d._1,
        d._2.map(t => (getContext(t), getDefinition(t))).toMap))

      val outputText = analyzeText(textFile, definitions, context)

      outputText.foreach(line => outputFile.write(line))

      outputFile.close()
    } catch {
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException => println("Oups! Its not you, its us!")
    }
  }

}
