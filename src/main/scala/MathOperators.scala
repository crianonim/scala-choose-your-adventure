package site.jans.screept
import scala.collection.mutable

object MathOperators {
  def toValue(
      list: Seq[String],
      ctx: mutable.Map[String, String]
  ): Seq[String] = {
    list map (Screept.getValue(_, ctx))
  }
  def mathOperation(
      list: Seq[String],
      operation: Function2[Double, Double, Double]
  ): Double = {
    list.map(_.toDouble).reduce(operation)
  }

  def helper(
      list: Seq[String],
      ctx: mutable.Map[String, String],
      operation: Function2[Double, Double, Double]
  ): String = {
    mathOperation(toValue(list, ctx), operation).toString()
  }
  val operators = mutable.Map(
    "+" -> Operator(2, (l, ctx) => helper(l, ctx, (_ + _))),
    "-" -> Operator(2, (l, ctx) => helper(l, ctx, (_ - _))),
    "*" -> Operator(2, (l, ctx) => helper(l, ctx, (_ * _))),
    "/" -> Operator(2, (l, ctx) => helper(l, ctx, (_ / _)))
  )

}
