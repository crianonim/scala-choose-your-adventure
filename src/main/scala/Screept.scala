package site.jans.game

import scala.collection.mutable.Stack
import scala.collection.mutable.Map

object Screept {
  type Context = Map[String, String]

  def getValue(operand: String, ctx: Context) = {
    ctx.getOrElse(operand, operand)
  }
  def toBoolean(x:Any):Boolean = {
    x match {
     case 0 => false
     case "" => false
     case null => false
     case _ => true
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
  case class Operator(arity: Int, f: Function2[Seq[String], Context, String])
  val operators = Map[String, Operator]()
  operators("+") = Operator(
    2,
    (l, ctx) =>
      (getValue(l(0), ctx).toDouble + getValue(l(1), ctx).toDouble).toString()
  )
  operators("-") = Operator(
    2,
    (l, ctx) =>
      (getValue(l(0), ctx).toDouble - getValue(l(1), ctx).toDouble).toString()
  )
  operators("*") = Operator(
    2,
    (l, ctx) =>
      (getValue(l(0), ctx).toDouble * getValue(l(1), ctx).toDouble).toString()
  )
  operators("/") = Operator(
    2,
    (l, ctx) =>
      (getValue(l(0), ctx).toDouble / getValue(l(1), ctx).toDouble).toString()
  )
  operators("?") = Operator(
    3,
    (l, ctx) =>
      (if (getValue(l(0), ctx) == "0") getValue(l(2), ctx)
       else getValue(l(1), ctx))
  )
  operators(":=") = Operator(2, (l, ctx) => {
    ctx(l(1)) = getValue(l(0), ctx); getValue(l(0), ctx)
  })
  operators("=") = Operator(
    2,
    (l, ctx) => if (getValue(l(0), ctx) == getValue(l(1), ctx)) "1" else "0"
  )
  def evaluate(tokens: Seq[String], ctx: Context) = {
    val stack = new Stack[String]()
    println("TOKENS", tokens)
    for (token <- tokens) {
      println("TOKEN:", token)
      if (operators contains token) {
        val operator = operators(token)
        val args = new Stack[String]()
        for (i <- (0 until operator.arity)) args.push(stack.pop())
        stack.push(operator.f(args.toList, ctx))
      } else {
        stack.push(token)
      }
      println("STACK", stack, "CTX", ctx)
    }

  }
}
// val evaluate=(tokens: Seq[String],ctx: Context)=>{
//   val stack=new Stack[String]()
//   println("TOKENS",tokens)
//   for (token<-tokens){
//     println("TOKEN:",token)
//     if (operators contains token){
//       val operator=operators(token)
//       val args=new Stack[String]()
//       for (i<- (0 until operator.arity)) args.push(stack.pop())
//       stack.push(operator.f(args,ctx))
//     }
//     else {
//       stack.push(token)
//     }
//     println("STACK",stack,"CTX",ctx)
//   }

// }
// evaluate(parseIntoTokens("100 30 = 'rowne' 'nie rowne' ?"),Map[String,String]())

// class Token()
// class Operator() extends Token
// case class Operand[T](literal:String,value:T) extends Token
// case class Operator1[T](name:String, n:Int, f:Function[Operand[T],Operand[T]]) extends Operator
// case class Operator2[T](name:String, n:Int, f:Function2[Operand[T],Operand[T],Operand[T]]) extends Operator
// case class OperandG(literal:String,value: Any)
// case class OperatorG(name:String,n:Int,f:Function2[Seq[OperandG],Map[String,OperandG],OperandG])
// val operators=Map[String,Operator]()
// var operatorsG=Map[String,OperatorG]();
// // val op1=Operand[Int]("val",(1+2))
// // val additionOp=Operator2[Int]("+",2, (x,y)=>Operand[Int]("val",(x.value+y.value)))
// // val multi=Operator2[Int]("*",2, (x,y)=>Operand[Int]("val",(x.value*y.value) ) )
// val getValue=(o:OperandG,ctx:Map[String,OperandG])=>{
//   if (ctx contains o.value.toString()){
//     (ctx.get(o.value.toString()).get).value.toString()
//   } else {
//     o.value.toString()
//   }
// }
// // operators("+")=additionOp
// // operators("*")=multi
// operatorsG("+")=OperatorG("+",2,(l,ctx)=>OperandG("v",l map(x=>getValue(x,ctx).toInt) reduce((a,c)=>a+c)))
// operatorsG("*")=OperatorG("*",2,(l,ctx)=>OperandG("v",l map(x=>getValue(x,ctx).toInt) reduce((a,c)=>a*c)))
// operatorsG("-")=OperatorG("-",2,(l,ctx)=>OperandG("v",l map(x=>getValue(x,ctx).toInt) reduce((a,c)=>a-c)))
// operatorsG(":=")=OperatorG(":=",2,(l,ctx)=>{
//   val t=l match {
//     case Seq(x,y)=>(x,y)
//   }
//   val result= OperandG("v",l map(x=>x.value) reduce(
//     (acc,cur)=>acc)
//   )
//   val value=t._1
//   val name=t._2.value.toString()
//   // ctx(cur.toString())=result
//   ctx(name)=value
//   result
// }
//   )
// println(operatorsG)

// object Screept {
//     val stack=new Stack[OperandG]()
//     val context=Map[String,OperandG]()

//     def printStack():Unit ={
//       println(stack)
//     }
//     def printContext():Unit={
//       println(context)
//     }
//     def parseString(s: String): Unit = {

//       s.split(" ").map(
//         x=>{
//           if (operatorsG contains x){
//             val o=(operatorsG get x).get
//             // val o2=o.asInstaceOf[Operator2[Int]]
//             println("Found operator "+o)
//             val args=Stack[OperandG]()
//             (1 to o.n) map(_=>args.push(stack.pop()))
//             stack.push(o.f(args,context))

//           }
//           else {
//             stack.push(OperandG(x,x))
//             println(stack)
//           }
//         }
//         )
//     }
// }
// Screept.parseString()
// Screept.printStack()
// Screept.printContext()
