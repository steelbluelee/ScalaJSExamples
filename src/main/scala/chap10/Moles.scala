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

@JSExportTopLevel("Moles")
object Moles {
  type Mole = dom.html.Div
  private var moles: Set[dom.html.Div] = Set()
  val W                                = 50
  val SPACE                            = 10

  @JSExport
  def main(): Unit = {
    makeAMoles(7, 4)
  }

  private def makeAMoles(nx: Int, ny: Int) = {
    for (i <- 0 to nx - 1;
         j <- 0 to ny - 1) {
      val element = makeAMole(i, j)
      js.Dynamic.global.document.body.appendChild(element)
      moles += element
      registerEventHandler(element)
    }
    moles
  }

  private def makeAMole(i: Int, j: Int): Mole = {

    // The following one line is same as
    //
    // val element = js.Dynamic.global.document
    //   .createElement("div")
    //   .asInstanceOf[dom.html.Div]
    val element = div.render

    element.style.width      = W + "px"
    element.style.height     = W + "px"
    element.style.background = "url(../images/mole.jpg)"
    element.style.position   = "absolute"
    element.style.opacity    = 1.0.toString
    element.style.transition = "transform 0.5s ease-in-out, opacity 0.5s ease"

    element.style.left = SPACE + i * (W + SPACE) + "px"
    element.style.top  = 2 * SPACE + j * (W + SPACE) + "px"

    js.Dynamic.global.console.log(element.style.left)
    js.Dynamic.global.console.log(element.style.top.toString)

    element
  }

  private def registerEventHandler(element: dom.html.Div): Unit =
    element.onclick = (e: MouseEvent) => {
      val element = e.currentTarget.asInstanceOf[dom.html.Div]
      js.Dynamic.global.console.log(moles.size)
      if (element.style.opacity.toDouble > 0.5) {
        js.Dynamic.global.document.body.removeChild(element)
        moles -= element
      }
      js.Dynamic.global.console.log(moles.size)
    }
}
