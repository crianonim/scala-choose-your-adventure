package site.jans.screept
import scala.collection.mutable

object BasicOperators {
  def toValue(
      list: Seq[String],
      ctx: mutable.Map[String, String]
  ): Seq[String] = {
    list map (Screept.getValue(_, ctx))
  }
  def isEqual(l: Seq[Any]): Boolean = {
    l(0) == l(1)
  }
  val operators = mutable.Map(
    "=" -> Operator(2, (l, ctx) => if (isEqual(toValue(l, ctx))) "1" else "0"),
    ":=" -> Operator(2, (l, ctx) => {
      ctx(l(1)) = Screept.getValue(l(0), ctx); Screept.getValue(l(0), ctx)
    }),
    "?" -> Operator(
      3,
      (l, ctx) =>
        if (Screept.getValue(l(2), ctx) == "0") Screept.getValue(l(1), ctx)
        else Screept.getValue(l(0), ctx)
    )
  )
}
