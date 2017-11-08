package parser

import java.io.{File, FileNotFoundException, IOException, PrintWriter}

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

      //outputFile.write(2)

      outputFile.close()
    } catch {
      case _: FileNotFoundException => println("Invalid file name!")
      case _: IOException => println("Oups! Its not you, its us!")
    }
  }

  /** For an input like
    * [tamplarie] piesa mobiliera pe care se sta
    * returns tamplarie
    *
    * It does so by splitting the input into a tuple: ("[tamplarie", "] piesa mobiliera pe care se sta").
    *
    * After that, it is wanted to get the context, so only _1 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty space or [.
    *
    *
    * @param t - a singular definition
    * @return - the context of that definition
    */
  private def getContext(t: String): String = t.span(_ != ']')._1.dropWhile(c => c == ' ' || c == '[')

  /** For an input like
    * [tamplarie] piesa mobiliera pe care se sta
    * returns piesa mobiliera pe care se sta
    *
    * It does so by splitting the input into a tuple: ("[tamplarie", "] piesa mobiliera pe care se sta").
    *
    * After that , it is wanted to get the definition, so only _2 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty or it is "]".
    *
    *
    * @param t - a singular definition
    * @return - the definition of that definition
    */
  private def getDefinition(t: String): String = t.span(_ != ']')._2.dropWhile(c => c == ' ' || c == ']')

  private def analyzeText(text: List[String], dictionary: List[Definition], context: List[Context]): List[String] = {
    def go(text: List[String], dictionary: List[Definition],
           context: List[Context],
           rowIndex: Int): List[String] = {
      if (text.isEmpty)
        List.empty
      else {
        val wordsFound = text.head.split(" ")
          .toList
          .map(w => (w, classifyWord(w, text.head)))

        val wordsDefined: StringBuilder = new StringBuilder

        wordsFound.foreach {
          case (_, None) =>
          case (word, Some(c)) => dictionary.find(d => d.word == word.toLowerCase()) match {
            case None =>
            case Some(definition) =>
              wordsDefined.append(s"""Line $rowIndex, $word = ${definition.definitions.getOrElse(c, c)} \n""")
          }
        }

        List(wordsDefined.toString()) ::: go(text.tail, dictionary, context, rowIndex + 1)
      }
    }

    def classifyWord(word: String, sentence: String): Option[String] = {
      val sentenceWords = sentence.split(" ").toList.filterNot(_ == word).map(_.toLowerCase())
      val wordDefinition = dictionary.filter(_.word == word.toLowerCase())

      //for each possible definitions, count how many words that describe that certain context
      // are found in the sentence
      // The one with the highest count, is returned.

      val contexts = wordDefinition.flatMap(definition => definition.definitions
        .toList
        .map(d => (d._1, count(d._1, sentenceWords))))

      if (contexts.nonEmpty)
        if (contexts.maxBy(_._2)._2 > 0)
          Some(contexts.maxBy(_._2)._1)
        else
          Some("Not enough context for the word to find a proper definition!")
      else
        None
    }

    //left todo
    /*
      1. sparse, break, cut and remove all words to singular form ( so they can be classified correctly )
      2. go through x and find the max occurence of a context > 0 and return the definition of the context
      3. return string, if exists , as:
        Word (line, indexOfWord) - definition.
     */

    def count(propriety: String, sentence: List[String]): Int = {
      context.find(c => c.propriety == propriety) match {
        case None => 0
        case Some(con) => con.contextOf.foldLeft(0)((total, curr) =>
          if (sentence.contains(curr)) total + 1
          else total
        )
      }
    }

    go(text, dictionary, context, 0)
  }
}
