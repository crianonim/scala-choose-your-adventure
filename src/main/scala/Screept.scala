package site.jans.screept

import scala.collection.mutable.Stack
import scala.collection.mutable

case class Operator(
    arity: Int,
    f: Function2[Seq[String], mutable.Map[String, String], String]
)

object Screept {
  type Context = mutable.Map[String, String]

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
    val tokens = new mutable.ListBuffer[String]()
    var ct = "";
    var quotes = false;
    for (c <- s) {
      c match {
        case '\'' => quotes = !quotes
        case ' ' =>
          if (quotes) ct += c
          else {
            tokens +=ct
            ct = ""
          }
        case _ => { ct += c }
      }
    }
    tokens+=ct
    tokens.filter(x => x != "").result()
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
        val result=operator.f(args.toList, ctx)
        if (operator.arity>0){
          stack.push(result)
        }
      } else {
        stack.push(token)
      }
      // println("STACK", stack, "CTX", ctx)
    }
    getValue(stack.pop(),ctx)
  }
}
