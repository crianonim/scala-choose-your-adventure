package site.jans.game
import scala.collection.mutable

case class GameServer(scenarios: List[Scenario]){
   private var id=0
   private val playthroughs=mutable.Map[Int,Playthrough]()
    def startGame(scenario_id:Int)={
        val scenario=scenarios(scenario_id)
        val newGame=Playthrough(scenario,scenario.newContext)
        id=id+1
        playthroughs(id)=newGame
        id
    }
    def getGame(playthrough_id:Int)={
        playthroughs(playthrough_id)
    }
}