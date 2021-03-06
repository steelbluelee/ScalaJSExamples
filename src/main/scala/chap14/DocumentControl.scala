package ScalaJSExamples

import scala.annotation.tailrec
import scala.scalajs.js.annotation.{JSExportTopLevel, JSExport}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js
import scala.scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.raw.{Event, MouseEvent, Element, Document}
import org.scalajs.dom.raw.{
  NodeList,
  Element,
  Node,
  HTMLCollection,
  ParentNode,
  Attr
}
import scalatags.JsDom.all._

import cats._
import cats.implicits._
import cats.data.State

// see https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/html.scala
// see the abstract class HTMLCollection at
// https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/Html.scala
// see the trait DOMList which is the parent of HTMLCollection at
// https://github.com/scala-js/scala-js-dom/blob/master/src/main/scala/org/scalajs/dom/raw/lib.scala

@JSExportTopLevel("DocumentControl")
object DocumentControl {
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
    dogs(1).nextElementSibling.innerHTML   = "웰시 코기<br>"
    for (i <- 0 to dogs.length - 1) {
      g.console.log(i + "번째의 값 : " + dogs(i).getAttribute("value"))

    }
  }

  @JSExport
  def getProperty(): Unit = {
    val anchor = g.document.getElementById("scala")
    g.console.log(anchor.href)
  }

  // I geuss set the return type of Scala.js's get functions to
  // [js.Array[html.Object]] insead of HTMLCollection or NodeList.
  // Because js.Array operate like Scala's mutable.Seq
  // But sometimes I must use it as type of HTMLCollection which
  // doesn't support foreach method so have to be used with for loop.
  @JSExport
  def getAttribute(): Unit = {
    val fm   = g.document.getElementById("favorite")
    val list = fm.children

    val result = list.asInstanceOf[js.Array[html.Object]].filter {
      (el: html.Object) =>
        el.nodeName == "INPUT" && el.`type` == "checkbox"
    }

    g.console.log(result.join(","))
  }

  @JSExport
  def setAttribute(): Unit = {
    val anchor = g.document.getElementById("scala")
    anchor.setAttribute("href", "https://scala-js.org")
    g.console.log(anchor)
  }

  // attributes's type is NamedNodeMap. It seems that it be treated as
  // js.Array[Attr]. As NamedNodeMap, you have to use for loop to iterate it.
  // As js.Array[Attr], you can foreach method to iterate it.
  @JSExport
  def getAttributes(): Unit = {
    val para = g.document.getElementById("controls").asInstanceOf[ParentNode]
    val list = para.firstElementChild.attributes

    for (i <- 0 to list.length - 1) {
      g.console.log("NamedNodeMap => " + list(i).name + ": " + list(i).value)
    }

    list.asInstanceOf[js.Array[Attr]].foreach { a =>
      g.console.log("js.Array[Attr] => " + a.name + ": " + a.value)
    }
  }

  // It's better to use scalatags in the following case
  @JSExport
  def appendChild(): Unit = {
    val doglist = g.document.getElementById("doglist")
    val element = g.document.createElement("li")
    val text    = g.document.createTextNode("불독")
    doglist.appendChild(element)
    element.appendChild(text)
  }

  // I've used scalatags instead of createElemnet function
  @JSExport
  def insertBefore(): Unit = {
    val doglist = g.document.getElementById("doglist").asInstanceOf[html.UList]
    val element = li("불독").render
    // children's type is HTMLCollection. If doglist.children(1) make an error,
    // you can fit it like this following code in comments
    // doglist.insertBefore(element,
    //                      doglist.children.asInstanceOf[js.Array[html.LI]](1))
    doglist.insertBefore(element, doglist.children(1))
  }

  @JSExport
  def moveNode(): Unit = {
    val doglist = g.document.getElementById("doglist").asInstanceOf[html.UList]
    doglist.appendChild(doglist.children(0))
  }

  @JSExport
  def scrollTo(): Unit = {
    // if (js.Object.hasProperty(g.history.asInstanceOf[js.Object], "scrollRestoration"))
    // in scala.js
    // is equivalent to
    // if ('scrollRestoration' in history)
    // in javascript
    // It's better to make implicit class which has in method or hasProperty method
    // for you to use like
    // if ("scrollRestoration" in g.history)
    // or
    // if (g.history.hasProperty("scrollRestoration")
    if (js.Object.hasProperty(g.history.asInstanceOf[js.Object],
                              "scrollRestoration"))
      g.history.scrollRestoration = "manual"

    val element = g.document.getElementById("sec3")
    val rect    = element.getBoundingClientRect()
    g.scrollTo(rect.left.asInstanceOf[Int] + GetScroll.left,
               rect.top.asInstanceOf[Int] + GetScroll.top)
    g.console.log("left : " + GetScroll.left)
    g.console.log("top  : " + GetScroll.top)
  }

  @JSExport
  def scrollIntoView(): Unit = {
    if (js.Object.hasProperty(g.history.asInstanceOf[js.Object],
                              "scrollRestoration"))
      g.history.scrollRestoration = "manual"

    val element = g.document.getElementById("sec3")
    element.scrollIntoView()
    g.console.log("left : " + GetScroll.left)
    g.console.log("top  : " + GetScroll.top)
  }

  @JSExport
  def htmlForm(): Unit = {
    val menu = g.document.getElementById("menu1").asInstanceOf[html.Select]
    g.console.log(menu)
    val options = g.document.getElementsByTagName("option")
    g.console.log(options)

    g.console.log(g.document.forms.item(0))
    g.console.log(g.document.forms.form1)
    g.console.log(g.document.forms.questions)

    g.console.log(g.document.forms.form1.elements.item(3))
    g.console.log(g.document.forms.form1.bloodtype)
    g.console.log(g.document.forms.form1.menu1)
  }

  @JSExport
  def cssControl(): Unit = {
    val element = g.document.getElementById("title")
    element.onclick = (_: MouseEvent) => element.style.backgroundColor = "pink"
  }

  @JSExport
  def createIconEditor(parent: html.Body, nx: Int, ny: Int): Unit = {
    val color                = input(`type` := "color").render
    val clear                = input(`type` := "button", value := "Clear All").render
    lazy val (aTable, cells) = makeATable

    clear.onclick = (e: MouseEvent) =>
      cells.foreach { (_td: html.TableCell) =>
        _td.style.backgroundColor = "white"
    }

    // aTable.style.borderCollapse = "collapse"
    // aTable.style.marginTop = "5px"

    parent.appendChild(color)
    parent.appendChild(clear)
    parent.appendChild(aTable)

    def makeATable: (html.Table, Seq[html.TableCell]) = {
      val (rows, cells) = Seq
        .fill(ny)(makeCellsForARow)
        .foldRight((Seq[html.TableRow](), Seq[html.TableCell]())) {
          (_aRow, z) =>
            (tr(_aRow).render +: z._1, _aRow ++ z._2)
        }
      (table(borderCollapse := "collapse", marginTop := "5px", rows).render,
       cells)
    }

    def makeCellsForARow = Seq.fill(nx)(makeACell)

    def makeACell = {
      val _td =
        td(width := "15px", height := "15px", border := "1px solid gray").render
      // td().render
      // _td.style.width  = "15px"
      // _td.style.height = "15px"
      // _td.style.border = "1px solid gray"
      _td.onclick = (e: MouseEvent) =>
        e.target.asInstanceOf[html.TableCell].style.backgroundColor =
          color.value
      _td
    }

  }

  @JSExport
  def getComputedStyle(): Unit = {
    val element = g.document.getElementById("note")
    g.console.log(element.style.color)
    g.console.log(element.style.height)

    val computedStyle = g.getComputedStyle(element)
    g.console.log(computedStyle.color)
    g.console.log(computedStyle.height)
  }

  @JSExport
  def classList(): Unit = {
    val element = g.document.getElementById("note1")
    val list    = element.classList.asInstanceOf[dom.DOMTokenList]
    list.asInstanceOf[js.Array[String]].foreach { s =>
      g.console.log(s)
    }
    list.toggle("invisible")
    g.console.log(element.className)
    list.toggle("invisible")
    g.console.log(element.className)
  }
}
