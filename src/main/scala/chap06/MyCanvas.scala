package ScalaJSExamples
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.util.Random

@JSExportTopLevel("MyCanvas")
object MyCanvas {
  @JSExport
  def main01(canvas: html.Canvas): Unit = {
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.strokeRect(50, 60, 200, 100)
  }
}
