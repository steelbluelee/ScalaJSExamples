package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import org.scalajs.dom

import scalatags.JsDom.all._

// set MouseEvent class at https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/lib.scala

@JSExportTopLevel("DragBox")
object DragBox {
  @JSExport
  def dragBox(): Unit = {

    val box = div(display := "inline-block",
                  position := "absolute",
                  padding := "10px",
                  backgroundColor := "blue",
                  color := "white",
                  cursor := "pointer",
                  "Scala.JS").render

    g.document.body.appendChild(box)

    box.addEventListener(
      "mousedown",
      (e: MouseEvent) => {
        val computedStyle = g.getComputedStyle(box)

        val offsetX = e.pageX.toDouble - computedStyle.left // format: off
                                          .asInstanceOf[String]
                                          .dropRight(2)
                                          .toDouble //format: on

        val offsetY = e.pageY.toDouble - computedStyle.top // format: off
                                          .asInstanceOf[String]
                                          .dropRight(2)
                                          .toDouble // format: on

        val mouseMoveListener = makeMouseMoveListener(offsetX, offsetY)

        g.document.addEventListener("mousemove", mouseMoveListener, false)

        g.document.addEventListener("mouseup", (_: MouseEvent) => {
          g.console.log("mouseup")
          g.document.removeEventListener("mousemove", mouseMoveListener, false)
        })

        g.console.log("mousedown")
      },
      false
    )

    def makeMouseMoveListener(offsetX: Double,
                              offsetY: Double): js.Function1[MouseEvent, Unit] =
      (e: MouseEvent) => {
        box.style.left = (e.pageX - offsetX) + "px"
        box.style.top  = (e.pageY - offsetY) + "px"
        g.console.log("left: " + box.style.left + " top: " + box.style.top)
      }

  }
}
