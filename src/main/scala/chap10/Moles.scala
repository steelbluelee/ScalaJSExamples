package ScalaJSExamples

import scala.util.{Try, Success, Failure}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import scalatags.JsDom.all._
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}

import cats._
import cats.implicits._
import cats.data.Reader

case class Mole(x: Int, y: Int, opacity: Double)

@JSExportTopLevel("Moles")
object Moles {
  @JSExport
  def main(): Unit = {
    makeMoles(7, 4)
  }

  def makeMoles(nx: Int, ny: Int) = {
    var molesState: Map[dom.html.Div, Mole] = Map()
    val W                                   = 50
    val SPACE                               = 10
    for (i <- 0 to nx - 1;
         j <- 0 to ny - 1) {
      val element = div.render

      // this code is same as the previouse code
      // val element = div.render
      //
      // val element = js.Dynamic.global.document
      //   .createElement("div")
      //   .asInstanceOf[dom.html.Div]

      element.style.width      = W + "px"
      element.style.height     = W + "px"
      element.style.background = "url(../images/mole.jpg)"
      element.style.position   = "absolute"
      element.style.opacity    = 0.2.toString
      element.style.transition = "transform 0.5s ease-in-out, opacity 0.5s ease"

      js.Dynamic.global.document.body.appendChild(element)

      element.style.left = SPACE + i * (W + SPACE) + "px"
      element.style.top  = 2 * SPACE + j * (W + SPACE) + "px"

      molesState = molesState + (element -> Mole(i, j, 0.2))

      element.onclick = (e: MouseEvent) => {
        val element = e.currentTarget.asInstanceOf[dom.html.Div]
        (molesState get element) foreach { state =>
          if (state.opacity >= 0.5) {
            js.Dynamic.global.document.body.removeChild(element)
            molesState -= element
          }
        }
      }
    }

    molesState
  }
}
