package scenario

import site.jans.game._

object Scen1 {

    def main(args: Array[String]): Unit = {
        Game.init(Game.readScenarioFile("scenario.txt"),"start")
    }
}