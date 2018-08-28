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

  @JSExport
  def readyStateChange(): Unit = {
    val req = new XMLHttpRequest()

    g.console.log(s"A: readyState = ${req.readyState}")

    req.onreadystatechange = (_: Event) =>
      g.console.log(s"B: readyState = ${req.readyState}")

    req.open("GET", "data.txt")
    req.send()
  }

  @JSExport
  def load(): Unit = {
    val req = new XMLHttpRequest()

    req.addEventListener("load",
                         (_: Event) =>
                           g.document.getElementById("view").innerHTML =
                             req.responseText,
                         false)
    req.open("GET", "data.txt")
    req.send()
  }

  @JSExport
  def loadstart(): Unit = {
    val req = new XMLHttpRequest()

    req.addEventListener("loadstart",
                         (_: Event) =>
                           g.document.getElementById("view").innerHTML =
                           "on processing",
                         false)
    req.open("GET", "data.txt")
    req.send()
  }

  @JSExport
  def error(): Unit = {
    val req = new XMLHttpRequest()

    req.addEventListener("error",
                         (_: Event) =>
                           g.document.getElementById("view").innerHTML =
                           "Server Error",
                         false)
    req.open("GET", "data.txt")
    req.send()
  }

  //see https://github.com/scala-js/scala-js/blob/master/library/src/main/scala/scala/scalajs/js/JSON.scala
  @JSExport
  def responseText(): Unit = {
    val req = new XMLHttpRequest()

    req.addEventListener("load", (_: Event) => {
      val jsonObj = js.JSON.parse(req.responseText)
      g.console.log(jsonObj)
    }, false)

    req.open("GET", "data.json", true)
    req.send()
  }

  @JSExport
  def response(): Unit = {
    val req = new XMLHttpRequest()

    req.addEventListener("load", (_: Event) => {
      val jsonObj = req.response
      g.console.log(jsonObj)
    }, false)

    req.responseType = "json"
    req.open("GET", "data.json", true)
    req.send()
  }
}
