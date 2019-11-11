package site.jans.screept

import scala.collection.mutable.Stack
import scala.collection.mutable.Map

case class Operator(
    arity: Int,
    f: Function2[Seq[String], Map[String, String], String]
)

object Screept {
  type Context = Map[String, String]

  def getValue(operand: String, ctx: Context) = {
    ctx.getOrElse(operand, operand)
  }
  def toBoolean(x: Any): Boolean = {
    x match {
      case 0    => false
      case ""   => false
      case null => false
      case _    => true
    }
  }
  def parseIntoTokens(s: String) = {
    val tokens = new Stack[String]()
    var ct = "";
    var quotes = false;
    for (c <- s) {
      c match {
        case '\'' => quotes = !quotes
        case ' ' =>
          if (quotes) ct += c
          else {
            tokens.push(ct); ct = ""
          }
        case _ => { ct += c }
      }
    }
    tokens.push(ct)
    tokens.filter(x => x != "").reverse.toList
  }

  def evaluate(operators: Map[String, Operator])(ctx: Context)(text: String) = {
    val tokens = parseIntoTokens(text)
    val stack = new Stack[String]()
    // println("TOKENS", tokens)
    for (token <- tokens) {
      // println("TOKEN:", token)
      if (operators contains token) {
        val operator = operators(token)
        val args = new Stack[String]()
        for (i <- (0 until operator.arity)) args.push(stack.pop())
        stack.push(operator.f(args.toList, ctx))
      } else {
        stack.push(token)
      }
      // println("STACK", stack, "CTX", ctx)
    }
    stack.pop()
  }
}
