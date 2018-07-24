package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import org.scalajs.dom
import dom.document

@JSExportTopLevel("NewPage")
object NewPage {
  @JSExport
  def main(): Unit = {
    var w: js.UndefOr[dom.Window] = js.undefined

    g.document.getElementById("open").onclick = (_: MouseEvent) =>
      w = g
        .open("newpage.html", "new page", "width=400, height=300")
        .asInstanceOf[dom.Window]

    g.document.getElementById("close").onclick = (_: MouseEvent) =>
      w foreach { _.close() }
  }
}
