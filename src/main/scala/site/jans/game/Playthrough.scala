package site.jans.game
import scala.collection.mutable

case class Playthrough(scenario: Scenario, ctx: mutable.Map[String, String]){
    def dialog()={
        scenario.dialogs(ctx("dialog"))
    }
    def eval(toRun:String)={
        scenario.run(ctx,toRun)
    }
    def interpolate(toInterpolate:String)={
        scenario.interpolate(ctx,toInterpolate)
    }
    var validOptions=evaluateValidOptions()
    
    def evaluateValidOptions()={
        dialog().options.filter(x=>x.condition.size==0 || eval(x.condition)!="0")
    }
    def show()={
        val d=dialog();
        ("Head",interpolate(d.title),validOptions map (_.text))
    }
    def play(option:Int)={
        val toRun =validOptions(option - 1).run
        val result = eval(toRun)
        if (result(0) == '#')
          ctx("dialog") = result.tail
        validOptions=evaluateValidOptions()
    }
}