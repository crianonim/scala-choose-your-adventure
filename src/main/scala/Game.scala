package site.jans.game

import scala.io.Source
import scala.collection.mutable
import site.jans.screept._

object Game {

  def init(
      scenarioText: String,
      startDialogName: String,
      ctx: mutable.Map[String, String],
      operators: Map[String,Operator]
  ): Unit = {
    gameLoop(getScenario(scenarioText), startDialogName, ctx, operators)
  }

  def gameLoop(
      dialogs: Map[String, Dialog],
      dialogName: String,
      ctx: mutable.Map[String, String],
      operators: Map[String,Operator]

  ): Unit = {
    var current = dialogName;
    var playing = true;
    val eval=Screept.evaluate(operators)(ctx) _
    while (playing) {
      println("CONTEXT:",ctx)
      val dialog=dialogs.get(current).get
      println("TITLE: "+dialog.title)
      val validOptions=dialog.options.filter(x=>x.condition.size==0 || eval(x.condition)!="0")
      println(validOptions.zipWithIndex map(x=>(x._2+1)+") "+x._1.text) mkString("\n") )
      val option = scala.io.StdIn.readInt()
      if (option == 0) {
        playing = false
      } else {
        val toRun =validOptions(option - 1).run
        val result = eval(toRun)
        if (result(0) == '#')
          current = result.tail
      }
    }
  }
  def readScenarioFile(fileName: String): String = {
    Source.fromFile(fileName).getLines.mkString("\n")
  }

  def getScenario(scenarioText: String): Map[String, Dialog] = {
    var listOfDial = List.empty[(String, Dialog)]

    val IdExtractor = """^#(.+)""".r
    val EmpyExtractor = """^$""".r
    val OptionExtractor = """^- (.+) \[(.*)\] (.+)$""".r

    // currently built Dialog parts
    var id: String = ""
    var text = "";
    var options = Vector.empty[DialogOption]

    def addDialog(): Unit = {
      val dial = Dialog(id, text, options)
      listOfDial = (id, dial) :: listOfDial
      id = "";
      text = "";
      options = Vector.empty[DialogOption]
    }
    for (line <- scenarioText.split("\n")) {
      line match {
        case EmpyExtractor()      => addDialog()
        case IdExtractor(foundId) => id = foundId
        case OptionExtractor(text,condition, run) =>
          options = options :+ DialogOption(text, condition, run)
        case _ => text += line + "\n"
      }
    }
    addDialog()
    listOfDial.toMap
  }
}
