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
    def cliPlay(playthrough: Playthrough)={
        var playing=true
        do {
            val dialog=playthrough.show();
            println(dialog._1);
            println(dialog._2);
            println(dialog._3.zipWithIndex map(x=>(x._2+1)+") "+x._1) mkString("\n"));
            println("0) Exit")

         val option = scala.io.StdIn.readInt()
         if (option == 0) {
           playing = false
         } else {
           playthrough.play(option)
         }
        } while (playing==true)
    }
}