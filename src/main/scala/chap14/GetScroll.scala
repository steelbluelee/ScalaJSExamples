package ScalaJSExamples

import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js

object GetScroll {
  val asInt = (v: js.Dynamic) => v.asInstanceOf[Int]

  def y: Int = {
    val pageYOffset: js.UndefOr[js.Dynamic] = g.window.pageYOffset
    val elementScrollTop: js.UndefOr[js.Dynamic] =
      g.document.documentElement.scrollTop
    val bodyScrollTop: js.UndefOr[js.Dynamic] = g.document.body.scrollTop

    js.Math.max(
      pageYOffset.fold(0) { asInt },
      elementScrollTop.fold(0) { asInt },
      bodyScrollTop.fold(0) { asInt }
    )
  }

  def top = y

  def x: Int = {
    val pageXOffset: js.UndefOr[js.Dynamic] = g.window.pageXOffset
    val elementScrollLeft: js.UndefOr[js.Dynamic] =
      g.document.documentElement.scrollLeft
    val bodyScrollLeft: js.UndefOr[js.Dynamic] = g.document.body.scrollLeft

    js.Math.max(
      pageXOffset.fold(0) { asInt },
      elementScrollLeft.fold(0) { asInt },
      bodyScrollLeft.fold(0) { asInt }
    )
  }

  def left = x
}
