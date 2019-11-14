package other
import site.jans.game._
import site.jans.screept.MathOperators
import site.jans.screept.LogicOperators
import site.jans.screept.BasicOperators
object Other {
    def main(args: Array[String]):Unit = {
        val operators=MathOperators.operators++LogicOperators.operators++BasicOperators.operators
        val s1=Scenario("pierwsze",Scenario.readScenarioFile("scenario.txt"),operators)
        println(s1.dialogs)
    }
}