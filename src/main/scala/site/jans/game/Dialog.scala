package site.jans.game
import site.jans.screept.Screept
import scala.collection.mutable
case class DialogOption(text:String,condition:String,run:String)

case class Dialog(id:String,title: String, options:Vector[DialogOption]){
   def display():String={
    val titleString=title+"\n "
    // val validOptions=options.filter(_.condition.size==0 || Screept)
    val optionsString=options.zipWithIndex map(x=>(x._2+1)+") "+x._1.text) mkString("\n ")
    titleString+optionsString+"\n"
  }
}
