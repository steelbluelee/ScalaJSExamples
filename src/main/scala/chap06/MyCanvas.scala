package ScalaJSExamples
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html

@JSExportTopLevel("MyCanvas")
object MyCanvas {
  @JSExport
  def main01(canvas: html.Canvas): Unit = {
    val ctx = canvas
      .getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.strokeRect(10, 10, 200, 150)
    ctx.fillRect(50, 40, 120, 90)
    ctx.clearRect(90, 65, 40, 40)
  }

  @JSExport
  def main02(canvas: html.Canvas): Unit = {
    val ctx = canvas
      .getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]
    val image =
      dom.document.createElement("img").asInstanceOf[dom.raw.HTMLImageElement]
    image.src = "../images/cat.jpg"
    image.onload = (e: dom.Event) => {
      ctx.drawImage(image, 0, 0)
    }
  }
}
