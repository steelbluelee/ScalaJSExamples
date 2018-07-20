package ScalaJSExamples

import scala.util.{Try, Success, Failure}
import scala.util.Random

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import scalatags.JsDom.all._
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}

@JSExportTopLevel("MoleGame")
object MoleGame {
  type Mole  = dom.html.Div
  type Moles = Set[Mole]
  private var _moles: Moles                          = Set()
  var timer: js.UndefOr[js.timers.SetIntervalHandle] = js.undefined
  val W                                              = 50
  val SPACE                                          = 10
  val TIME_INTERVAL                                  = 1500
  val DISPLAY_TIME                                   = 1050

  @JSExport
  def main(): Unit = {
    makeAMoles(7, 4)
    timer = js.timers.setInterval(TIME_INTERVAL)(startGame)
  }

  private def makeAMoles(nx: Int, ny: Int) = {
    for (i <- 0 to nx - 1;
         j <- 0 to ny - 1) {
      val mole = makeAMole(i, j)
      js.Dynamic.global.document.body.appendChild(mole)
      _moles += mole
    }
    _moles
  }

  private def makeAMole(i: Int, j: Int): Mole = {

    // The following one line is same as
    //
    // val element = js.Dynamic.global.document
    //   .createElement("div")
    //   .asInstanceOf[Mole]
    val mole = div.render

    mole.style.width      = W + "px"
    mole.style.height     = W + "px"
    mole.style.background = "url(../images/mole.jpg)"
    mole.style.position   = "absolute"
    mole.style.opacity    = 0.2.toString
    mole.style.transition = "transform 0.5s ease-in-out, opacity 0.5s ease"

    mole.style.left = SPACE + i * (W + SPACE) + "px"
    mole.style.top  = 2 * SPACE + j * (W + SPACE) + "px"

    registerEventHandler(mole)

    // js.Dynamic.global.console.log(mole.style.left)
    // js.Dynamic.global.console.log(mole.style.top.toString)

    mole
  }

  private def registerEventHandler(mole: Mole): Unit =
    mole.onclick = (e: MouseEvent) => {
      val mole = e.currentTarget.asInstanceOf[Mole]
      // js.Dynamic.global.console.log(_moles.size)
      if (mole.style.opacity.toDouble > 0.5) {
        js.Dynamic.global.document.body.removeChild(mole)
        _moles -= mole
      }
      // js.Dynamic.global.console.log(_moles.size)
    }

  private def startGame: Unit =
    if (_moles.size == 0)
      stopGame
    else
      continueGame

  private def stopGame: Unit = {
    timer foreach js.timers.clearInterval
    js.Dynamic.global.document.body.innerHTML = "game over"
  }

  private def continueGame = {
    val r    = Random
    val mole = _moles.toList(r nextInt (_moles.size))

    mole.style.opacity   = 1.toString
    mole.style.transform = "translateY(-10px)"

    js.timers.setTimeout(DISPLAY_TIME) {
      mole.style.opacity   = 0.2.toString
      mole.style.transform = "translateY(0px)"
    }
  }
}
