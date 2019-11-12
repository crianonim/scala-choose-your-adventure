package site.jans.screept
import scala.collection.mutable

object LogicOperators {
  def toValue(
      list: Seq[String],
      ctx: mutable.Map[String, String]
  ): Seq[String] = {
    list map (Screept.getValue(_, ctx))
  }
  def toBoolean(value:Double):Boolean = {
    value match {
        case 0 => false
        case _ => true
    }
  }
  def logicOperation(
      list: Seq[String],
      operation: Function2[Boolean, Boolean, Boolean]
  ): Boolean = {
    list.map(_.toDouble).map(toBoolean(_)).reduce(operation)
  }

  def helper(
      list: Seq[String],
      ctx: mutable.Map[String, String],
      operation: Function2[Boolean, Boolean, Boolean]
  ): String = {
    if (logicOperation(toValue(list, ctx), operation)) "1" else "0"
  }

  val operators = Map(
    "&"->Operator(2,(l,ctx)=>helper(l,ctx,(_&&_))),
    "|"->Operator(2,(l,ctx)=>helper(l,ctx,(_||_))),
    "!"->Operator(1,(l,ctx)=>if (toBoolean(l(0).toDouble)) "0" else "1"
    )

  )
}