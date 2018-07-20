package ScalaJSExamples

import scala.util.{Try, Success, Failure}
import scala.util.Random

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
  type Mole  = dom.html.Div
  type Moles = Set[dom.html.Div]
  private var _moles: Set[dom.html.Div]              = Set()
  var timer: js.UndefOr[js.timers.SetIntervalHandle] = js.undefined
  val W                                              = 50
  val SPACE                                          = 10
  val TIME_INTERVAL                                  = 1500
  val DISPLAY_TIME                                   = 1050

  @JSExport
  def main(): Unit = {
    makeAMoles(7, 4)
    timer = js.timers.setInterval(TIME_INTERVAL)(game run _moles)
  }

  private def makeAMoles(nx: Int, ny: Int) = {
    for (i <- 0 to nx - 1;
         j <- 0 to ny - 1) {
      val element = makeAMole(i, j)
      js.Dynamic.global.document.body.appendChild(element)
      _moles += element
      registerEventHandler(element)
    }
    _moles
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
    element.style.opacity    = 0.2.toString
    element.style.transition = "transform 0.5s ease-in-out, opacity 0.5s ease"

    element.style.left = SPACE + i * (W + SPACE) + "px"
    element.style.top  = 2 * SPACE + j * (W + SPACE) + "px"

    // js.Dynamic.global.console.log(element.style.left)
    // js.Dynamic.global.console.log(element.style.top.toString)

    element
  }

  private def registerEventHandler(element: dom.html.Div): Unit =
    element.onclick = (e: MouseEvent) => {
      val element = e.currentTarget.asInstanceOf[dom.html.Div]
      // js.Dynamic.global.console.log(_moles.size)
      if (element.style.opacity.toDouble > 0.5) {
        js.Dynamic.global.document.body.removeChild(element)
        _moles -= element
      }
      // js.Dynamic.global.console.log(_moles.size)
    }

  private val game: Reader[Moles, Unit] = for {
    molesNum <- Reader((moles: Moles) => moles.size)
    _ <- if (molesNum == 0) // format: off
           Reader((_: Moles) => stopGame())
         else
           Reader((_: Moles) => continueGame(_moles))
         // format: on
  } yield ()

  private def stopGame(): Unit = {
    timer foreach js.timers.clearInterval
    js.Dynamic.global.document.body.innerHTML = "game over"
  }

  private def continueGame(moles: Moles) = {
    val r    = Random
    val mole = moles.toList(r nextInt (moles.size))
    mole.style.opacity   = 1.toString
    mole.style.transform = "translateY(-10px)"
    js.timers.setTimeout(DISPLAY_TIME) {
      mole.style.opacity   = 0.2.toString
      mole.style.transform = "translateY(0px)"
    }
  }
}
