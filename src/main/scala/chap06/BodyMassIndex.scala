package ScalaJSExamples

import scala.util.{Try, Success, Failure}
import scala.scalajs.js.annotation.{JSExportTopLevel, JSExport}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import scalatags.JsDom.all._

@JSExportTopLevel("BodyMassIndex")
object BodyMassIndex {
  @JSExport
  def main() : Unit = {
    val height = js.Dynamic.global.document.getElementById("height")
    val weight = js.Dynamic.global.document.getElementById("weight")
    val bmi = js.Dynamic.global.document.getElementById("bmi")
    val button = js.Dynamic.global.document.getElementById("button")

    button.onclick = (_ : MouseEvent) => run()

    def run() : Unit = {
      bmi.innerHTML = (for {
        h <- Try(height.value)
        w <- Try(weight.value)
        b <- Try(w/h/h)
      } yield b) fold (_.toString, "%.2f" format _)
    }
  }
}
