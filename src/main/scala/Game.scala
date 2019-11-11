package site.jans.game
import scala.io.Source
import scala.collection.mutable
import site.jans.screept._

object Game {
  def main(args: Array[String]): Unit = {
    println("Welcome to Choose Your Adventure!");
    val dialogs: Map[String, Dialog] = getScenario("scenario.txt")
    val eval=Screept.evaluate(MathOperators.operators ++ BasicOperators.operators)(mutable.Map[String,String]()) _
    eval("true false 0 ?")
    eval("5 a := 6 + 11 =")
    // gameLoop(dialogs, "start")
  }

  def gameLoop(dialogs: Map[String, Dialog], dialogName: String): Unit = {
    var current = dialogName;
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

  def getScenario(fileName: String): Map[String, Dialog] = {
    var listOfDial = List.empty[(String, Dialog)]

    val IdExtractor = """^#(.+)""".r
    val EmpyExtractor = """^$""".r
    val OptionExtractor = """^- (.+) -> #(.+)$""".r

     // currently built Dialog parts
     var id: String = ""
     var text = "";
     var options = Vector.empty[DialogOption]

    for (line <- Source.fromFile(fileName).getLines) {
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
