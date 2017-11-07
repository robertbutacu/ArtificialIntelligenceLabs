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
    } catch {
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException => println("Oups! Its not you, its us!")
    }
  }

  /** For an input like
    *   [tamplarie] piesa mobiliera pe care se sta
    * returns tamplarie
    *
    * It does so by splitting the input into a tuple: ("[tamplarie", "] piesa mobiliera pe care se sta").
    *
    * After that, it is wanted to get the context, so only _1 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty space or [.
    *
    * @param t - a singular definition
    * @return - the context of that definition
    */
  private def getContext(t: String): String = t.span(_ != ']')._1.dropWhile(c => c == ' ' || c == '[')

  /** For an input like
    *  [tamplarie] piesa mobiliera pe care se sta
    * returns piesa mobiliera pe care se sta
    *
    * It does so by splitting the input into a tuple: ("[tamplarie", "] piesa mobiliera pe care se sta").
    *
    * After that , it is wanted to get the definition, so only _2 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty or it is "]".
    *
    * @param t - a singular definition
    * @return - the definition of that definition
    */
  private def getDefinition(t: String): String = t.span(_ != ']')._2.dropWhile(c => c == ' ' || c == ']')
}
