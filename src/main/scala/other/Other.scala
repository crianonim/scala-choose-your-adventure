package other

import site.jans.game._
import site.jans.screept.{Operator, Screept}

import scala.annotation.varargs

object Other {
  def main(args: Array[String]): Unit = {
      def roll(d:Int):Int = {
          scala.util.Random.nextInt(d)+1
      }
    val customOperators = Map(
      "ROLL" -> Operator(1, (l, ctx) => (scala.util.Random.nextInt(l(0).toInt) + 1).toString),
      "FIGHT" -> Operator(1, (l, ctx) => {
          val enemy=Screept.getValue(l(0),ctx)
          println(enemy)
          val a=enemy.split('/')
          ctx("enemy_name")=a(0)
          ctx("enemy_skill")=a(1)
          ctx("enemy_stamina")=a(2)
          "#combat"
      }),
        "ATTACK"->Operator(0,(_,ctx)=>{
            var enemyStamina=ctx("enemy_stamina").toInt
            var playerStamina=ctx("stamina").toInt
            val enemyTotal=ctx("enemy_skill").toInt+roll(6)+roll(6);
            println("Enemy total: "+enemyTotal);
            val playerTotal=ctx("skill").toInt+roll(6)+roll(6);
            println("Player total: "+playerTotal)
            if (playerTotal > enemyTotal) {
                enemyStamina=enemyStamina-2
            }
            if (playerTotal < enemyTotal){
                playerStamina=playerStamina-2
            }
            ctx("stamina")=playerStamina.toString
            ctx("enemy_stamina")=enemyStamina.toString
            "#combat"
        })

    );
    val operators = Screept.getCoreOperators() ++ customOperators;
    val initialCtx = Map("dialog" -> "start", "turn" -> "1", "goblin" -> "goblin/7/10", "wolf" -> "wolf/9/8","skill"->"9","stamina"->"12")
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