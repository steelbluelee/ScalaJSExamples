package ScalaJSExamples

import scala.scalajs.js.annotation.{JSExportTopLevel, JSExport}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import org.scalajs.dom.raw.{NodeList, Element, Node, HTMLCollection}
// import scalatags.JsDom.all._

@JSExportTopLevel("GetNodeObject")
object GetNodeObject {
  @JSExport
  def getElementById(): Unit = {
    val element = g.document.getElementById("second").asInstanceOf[html.Div]
    element.innerHTML = "changed here"
  }

  @JSExport
  def getElementsByTagName(): Unit = {
    // js.Array <-> mutable.Seq, It seems to be HTMLCollection
    val elements =
      g.document.getElementsByTagName("div").asInstanceOf[js.Array[html.Div]]
    elements(2).innerHTML = "changed here"
  }

  @JSExport
  def getElementsByClassName01(): Unit = {
    val cats =
      g.document
        .getElementsByClassName("cat")
        .asInstanceOf[js.Array[html.Div]]
    for (i <- 0 to cats.length - 1) {
      g.console.log(i + " 번째 고양이" + cats(i).innerHTML)
    }
  }

  @JSExport
  def getElementsByClassName02(): Unit = {
    val paras =
      g.document
        .getElementsByTagName("p")
        .asInstanceOf[js.Array[html.Div]]
    val firstParaImportants = paras(0).getElementsByClassName("important")
    g.console.log(firstParaImportants)
  }

  @JSExport
  def getElementsByName(): Unit = {
    val dogs =
      g.document.getElementsByName("dog").asInstanceOf[HTMLCollection]

    // It is likely that the nodeValue_= and value methods aren't implemented
    // in scala.js's Node and Element traits(or classes)
    // So, use nexElementSibling with <span></span> tag instead of
    // nextSibling. The following commentted codes don't work
    // dogs(1).value = "corgi"
    // dogs(1).nextSibling.nodeValue = "웰시 코기"

    dogs(1).asInstanceOf[html.Input].value = "corgi"
    dogs(1).nextElementSibling.innerHTML   = "웰시 코기"
    for (i <- 0 to dogs.length - 1) {
      g.console.log(i + "번째의 값 : " + dogs(i).getAttribute("value"))

    }
  }
}
