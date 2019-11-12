package site.jans.game

import scala.io.Source
import scala.collection.mutable
import site.jans.screept._

object Game {

  def init(
      scenarioText: String,
      startDialogName: String,
      ctx: mutable.Map[String, String]
  ): Unit = {
    gameLoop(getScenario(scenarioText), startDialogName, ctx)
  }

  def gameLoop(
      dialogs: Map[String, Dialog],
      dialogName: String,
      ctx: mutable.Map[String, String]
  ): Unit = {
    var current = dialogName;
    var playing = true
    while (playing) {
      println(ctx)
      println(dialogs.get(current).get.display())
      val option = scala.io.StdIn.readInt()
      if (option == 0) {
        playing = false
      } else {
        val toRun = dialogs.get(current).get.options(option - 1).run
        val result = Screept.evaluate(
          LogicOperators.operators ++ BasicOperators.operators ++ MathOperators.operators
        )(ctx)(toRun)
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
