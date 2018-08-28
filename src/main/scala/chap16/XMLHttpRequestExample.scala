package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import org.scalajs.dom.raw.{XMLHttpRequest}
import org.scalajs.dom

@JSExportTopLevel("XMLHttpRequestExample")
object XMLHttpRequestExample {

  @JSExport
  def gettingStarted(): Unit = {
    val req = new XMLHttpRequest()

    req.onreadystatechange = (_: Event) => {
      if (req.readyState == 4) {
        if (req.status == 200) {
          g.document.getElementById("view").innerHTML = req.responseText
        }

      }
    }

    req.open("GET", "data.txt")
    req.send()
  }
}
