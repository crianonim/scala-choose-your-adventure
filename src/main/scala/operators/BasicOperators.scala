package site.jans.screept
import scala.collection.mutable

object BasicOperators {
  def toValue(
      list: Seq[String],
      ctx: mutable.Map[String, String]
  ): Seq[String] = {
    list map (Screept.getValue(_, ctx))
  }
  def isEqual(l: Seq[String]): Boolean = {
    try {
      l(0).toDouble == l(1).toDouble
    } catch {
      case x: Throwable => l(0) == l(1)
    }
  }
  val noopOperator = Operator(0, (l, ctx) => "0")
  val operators = Map(
    "=" -> Operator(2, (l, ctx) => if (isEqual(toValue(l, ctx))) "1" else "0"),
    ":=" -> Operator(2, (l, ctx) => {
      ctx(l(1)) = Screept.getValue(l(0), ctx); Screept.getValue(l(0), ctx)
    }),
    "?" -> Operator(
      3,
      (l, ctx) =>
        if (Screept.getValue(l(2), ctx) == "0") Screept.getValue(l(1), ctx)
        else Screept.getValue(l(0), ctx)
    ),
    ";" -> noopOperator,
    "(" -> noopOperator,
    ")" -> noopOperator,
    "DEBUG" -> Operator(0, (l, ctx) => {println(ctx);"0"})
  )
}
