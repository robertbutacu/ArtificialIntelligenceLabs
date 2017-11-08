package parser

object SentenceAnalyzer {

  /** For an input like
    * [carpentry] piece of furniture for people to sit on
    * returns carpentry
    *
    * It does so by splitting the input into a tuple: ("[carpentry", "] piece of furniture for people to sit on").
    *
    * After that, it is wanted to get the context, so only _1 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty space or [.
    *
    *
    * @param t - a singular definition
    * @return - the context of that definition
    */
  def getContext(t: String): String = t.span(_ != ']')._1.dropWhile(c => c == ' ' || c == '[')

  /** For an input like
    * [carpentry] piece of furniture for people to sit on
    * returns piece of furniture for people to sit on
    *
    * It does so by splitting the input into a tuple: ("[carpentry", "] piece of furniture for people to sit on").
    *
    * After that , it is wanted to get the definition, so only _2 of the tuple, and remove any unnecessary chars.
    * => dropWhile it is empty or it is "]".
    *
    *
    * @param t - a singular definition
    * @return - the definition of that definition
    */
  def getDefinition(t: String): String = t.span(_ != ']')._2.dropWhile(c => c == ' ' || c == ']')


  def analyzeText(text: List[String], dictionary: List[Definition], context: List[Context]): List[String] = {

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
          case (_, None)          =>
          case (word, Some(c))    =>
            dictionary.find(d => d.word == simplify(word.toLowerCase())) match {
              case None             =>
              case Some(definition) =>
                wordsDefined.append(s"""Line $rowIndex, $word = ${definition.definitions.getOrElse(c, c)} \n""")
            }
        }

        List(wordsDefined.toString()) ::: go(text.tail, dictionary, context, rowIndex + 1)
      }
    }

    def classifyWord(word: String, sentence: String): Option[String] = {
      val sentenceWords = sentence.split(" ")
        .toList
        .filterNot(_ == word)
        .map(w => simplify(w.toLowerCase()))

      val wordDefinition = dictionary.filter(_.word == simplify(word.toLowerCase()))

      // for each possible definitions, count how many words that describe that certain context
      // are found in the sentence
      // The one with the highest count, is returned.

      val contexts = wordDefinition.flatMap(definition => definition.definitions
        .toList
        .map(d => (d._1, count(d._1, sentenceWords))))

      val bestMatch = if (contexts.nonEmpty) Some(contexts.maxBy(_._2)) else None

      val notEnoughContext = "Not enough context for the word to find a proper definition!"

      bestMatch match {
        case None    => None
        case Some(d) => if (d._2 > 0) Some(d._1) else Some(notEnoughContext)
      }
    }

    def count(propriety: String, sentence: List[String]): Int = {
      context.find(c => c.propriety == propriety) match {
        case None      => 0
        case Some(con) => con.contextOf.foldLeft(0)((total, curr) =>
          if (sentence.contains(curr)) total + 1
          else total
        )
      }
    }

    def simplify(word: String): String = {
      def doubleLetterPlural(input: String): Boolean =
        !dictionary.exists(e => e.word == word) && dictionary.exists(e => e.word == word.dropRight(2))

      def singleLetterPlural(input: String): Boolean =
        !dictionary.exists(e => e.word == word) && dictionary.exists(e => e.word == word.dropRight(1))

      if (singleLetterPlural(word))      word.dropRight(1)//trees    => tree
      else if (doubleLetterPlural(word)) word.dropRight(2)//branches => branch
      else                               word
    }

    go(text, dictionary, context, 0)
  }
}
