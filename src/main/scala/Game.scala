package site.jans.game
import scala.io.Source

object Game {
  def main(args: Array[String]): Unit = {
    println("Welcome to Choose Your Adventure!");
    val dialogs: Map[String, Dialog] = getScenario()
    // println(dialogs)
    gameLoop(dialogs, "start")
  }

  def gameLoop(dialogs: Map[String, Dialog], dialogName: String): Unit = {
    var current = "start";
    var playing = true
    while (playing) {
      println(dialogs.get(current).get.display())
      val option = scala.io.StdIn.readInt()
      if (option == 0) {
        playing = false
      } else {
        current = dialogs.get(current).get.options(option - 1).goTo
      }
    }
  }

  def getScenario(): Map[String, Dialog] = {
    var id: String = ""
    var text = "";
    var options = Vector.empty[DialogOption]
    var listOfDial = List.empty[(String, Dialog)]
    val IdExtractor = """^#(.+)""".r
    val EmpyExtractor = """^$""".r
    val OptionExtractor = """^- (.+) -> #(.+)$""".r
    for (line <- Source.fromFile("scenario.txt").getLines) {
      line match {
        case EmpyExtractor() =>
          val dial = Dialog(id, text, options)
          listOfDial = (id, dial) :: listOfDial
          id = "";
          text = "";
          options = Vector.empty[DialogOption]
        case IdExtractor(foundId) => id = foundId
        case OptionExtractor(text, goTo) =>
          options = options :+ DialogOption(text, goTo)
        case _ => text += line + "\n"
      }
    }
    listOfDial.toMap
  }
}
