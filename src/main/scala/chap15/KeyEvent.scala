package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document, KeyboardEvent}
import org.scalajs.dom

import scala.collection.mutable.StringBuilder

// see https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/lib.scala

@JSExportTopLevel("KeyEvent")
object KeyEvent {
  @JSExport
  def keyEvent(): Unit = {
    val display = g.document.getElementById("display")
    g.document.addEventListener("keydown", showKey _, false)

    def showKey(e: KeyboardEvent): Unit = {
      val s = new StringBuilder()

      s.append(s"<br> altKey : ${e.altKey}")
      s.append(s"<br> ctrlKey : ${e.ctrlKey}")
      s.append(s"<br> shiftKey : ${e.shiftKey}")
      s.append(s"<br> metaKey : ${e.metaKey}")
      s.append(s"<br> key : ${e.key}")
      s.append(s"<br> location : ${e.location}")
      s.append(s"<br> keyCode : ${e.keyCode}")
      s.append(s" -> ${e.keyCode.toChar}")

      display.innerHTML = s.toString
    }
  }

}
