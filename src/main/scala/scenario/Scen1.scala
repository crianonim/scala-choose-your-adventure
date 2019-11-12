package scenario

import site.jans.game._
import scala.collection.mutable
import site.jans.screept.BasicOperators
import site.jans.screept.MathOperators
import site.jans.screept.LogicOperators

object Scen1 {

    def main(args: Array[String]): Unit = {
        val ctx=mutable.Map("turn"->"1")
        val operators=BasicOperators.operators++MathOperators.operators++LogicOperators.operators
        Game.init(Game.readScenarioFile("scenario.txt"),"start",ctx,operators)
    }
}