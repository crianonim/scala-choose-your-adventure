package scenario

import site.jans.game._
import scala.collection.mutable

object Scen1 {

    def main(args: Array[String]): Unit = {
        val ctx=mutable.Map("turn"->"1")
        Game.init(Game.readScenarioFile("scenario.txt"),"start",ctx)
    }
}