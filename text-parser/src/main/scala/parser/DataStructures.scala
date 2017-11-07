package parser

case class Context(propriety: String, contextOf: List[String])

case class Definition(word: String, definitions: Map[String, String])
