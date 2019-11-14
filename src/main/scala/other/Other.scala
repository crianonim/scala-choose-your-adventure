package other
import site.jans.game._
import site.jans.screept.MathOperators
import site.jans.screept.LogicOperators
import site.jans.screept.BasicOperators
object Other {
    def main(args: Array[String]):Unit = {
        val operators=MathOperators.operators++LogicOperators.operators++BasicOperators.operators
        val initialCtx=Map("dialog"->"start","turn"->"1")
        val s1=Scenario("pierwszy scenariusz",Scenario.readScenarioFile("scenario.txt"),operators,initialCtx)
        // println(s1.dialogs)
        // println(s1.newContext)
        // val p1=Playthrough(s1,s1.newContext)
        // println(p1)
        // println("V",p1.validOptions)
        val gs=GameServer(List(s1))
        val gameId=gs.startGame(0)
        val p1=gs.getGame(gameId)
        println("SHOW",p1.show())
        p1.play(2);
        println("SHOW",p1.show())
        p1.play(1);
        println("SHOW",p1.show())
        val p2=gs.getGame(gs.startGame(0))
        println("SHOW",p2.show())

    }
}