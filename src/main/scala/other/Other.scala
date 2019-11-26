package other

import site.jans.game._
import site.jans.screept.{Operator, Screept}

import scala.annotation.varargs

object Other {
  def main(args: Array[String]): Unit = {
    val customOperators = Map(
      "ROLL" -> Operator(1, (l, ctx) => (scala.util.Random.nextInt(l(0).toInt)+1).toString)
    );
    val operators = Screept.getCoreOperators() ++ customOperators;
    val initialCtx = Map("dialog" -> "start", "turn" -> "1")
    val s1 = Scenario("pierwszy scenariusz", Scenario.readScenarioFile("scenario.txt"), operators, initialCtx)
    val s2 = Scenario("London Life", Scenario.readScenarioFile("scen2.txt"), operators, initialCtx)
    val s3 = Scenario("Journey", Scenario.readScenarioFile("scenario3.txt"), operators, initialCtx)
    // println(s1.dialogs)
    // println(s1.newContext)
    // val p1=Playthrough(s1,s1.newContext)
    // println(p1)
    // println("V",p1.validOptions)
    val gs = GameServer(List(s1, s2, s3))
    val gameId = gs.startGame(0)
    val p1 = gs.getGame(gameId)
    // println("SHOW",p1.show())
    // p1.play(2);
    // println("SHOW",p1.show())
    // p1.play(1);
    // println("SHOW",p1.show())
    // val p2=gs.getGame(gs.startGame(0))
    // println("SHOW",p2.show())
    // println(gs.getScenarios())
    // println(gs.getStartedGames())
    gs.cliPlay(p1)

  }
}