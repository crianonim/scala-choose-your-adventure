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
    for (line <- Source.fromFile("scenario.txt").getLines) {
      if (line.length() == 0) {
        val dial = Dialog(id, text, options)
        listOfDial = (id, dial) :: listOfDial
        id = "";
        text = "";
        options = Vector.empty[DialogOption]
      } else {
        if (line.startsWith("#")) {
          id = line.substring(1)
        } else if (line.startsWith("- ")) {
          val option = line.substring(2)
          val arr = option.indexOf("->")
          val text = option.substring(0, arr - 1)
          val goTo = option.substring(arr + 4)
          options = options :+ DialogOption(text, goTo)
        } else {
          text += line + "\n"
        }
      }
    }
    listOfDial.toMap
  }
}
