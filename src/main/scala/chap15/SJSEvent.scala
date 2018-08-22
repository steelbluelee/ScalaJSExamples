package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document, KeyboardEvent}
import org.scalajs.dom

import scala.collection.mutable.StringBuilder
// see https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/lib.scala

@JSExportTopLevel("SJSEvent")
object SJSEvent {

  @JSExport
  def eventPropagation(): Unit = {
    val outer  = g.document.getElementById("outer")
    val inner2 = g.document.getElementById("inner2")

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer bubbling"),
                           false)

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer capturing"),
                           true)

    inner2.addEventListener("click",
                            (_: MouseEvent) => g.console.log("inner2 bubbling"),
                            false)
  }

  @JSExport
  def stopPropagation(): Unit = {
    val outer  = g.document.getElementById("outer")
    val inner2 = g.document.getElementById("inner2")

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer bubbling"),
                           false)

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer capturing"),
                           true)

    inner2.addEventListener("click", // format: off
                            (e: MouseEvent) => {
                              g.console.log("inner2 (1)")
                              e.stopPropagation()
                            },
                            false) // format: on

    inner2.addEventListener("click",
                            (_: MouseEvent) => g.console.log("inner2 (2)"),
                            false)

  }


  @JSExport
  def stopImmediatePropagation(): Unit = {
    val outer  = g.document.getElementById("outer")
    val inner2 = g.document.getElementById("inner2")

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer bubbling"),
                           false)

    outer.addEventListener("click",
                           (_: MouseEvent) => g.console.log("outer capturing"),
                           true)

    inner2.addEventListener("click", // format: off
                            (e: MouseEvent) => {
                              g.console.log("inner2 (1)")
                              e.stopImmediatePropagation()
                            },
                            false) // format: on

    inner2.addEventListener("click",
                            (_: MouseEvent) => g.console.log("inner2 (2)"),
                            false)

  }
}
