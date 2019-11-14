package site.jans.game
import site.jans.screept._
import scala.collection.mutable
import scala.io.Source
case class Scenario(
    id: String,
    content: String,
    operators: Map[String, Operator],
    initialContext: Map[String, String]
) {
  def newContext = mutable.Map(initialContext.toSeq:_*)
  val dialogs = {
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
    for (line <- content.split("\n")) {
      line match {
        case EmpyExtractor()      => addDialog()
        case IdExtractor(foundId) => id = foundId
        case OptionExtractor(text, condition, run) =>
          options = options :+ DialogOption(text, condition, run)
        case _ => text += line + "\n"
      }
    }
    addDialog()
    listOfDial.toMap
  }
  def run(ctx: mutable.Map[String, String], toRun: String) = {
    Screept.evaluate(operators)(ctx)(toRun)
  }
}
object Scenario {
  def readScenarioFile(fileName: String): String = {
    Source.fromFile(fileName).getLines.mkString("\n")
  }
}
