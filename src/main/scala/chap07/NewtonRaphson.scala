package ScalaJSExamples

import scala.util.{Try, Success, Failure}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html
import scala.scalajs.js

import cats._
import cats.implicits._
import cats.data.State

@JSExportTopLevel("NewtonRaphson")
object NewtonRaphson {
  @JSExport
  def main01(): Unit = {
    val EPSILON = 10e-10

    val t = js.Dynamic.global.prompt("뉴턴-랩슨 법으로 제곱근 구하기")

    val answer = Try(t.asInstanceOf[String].toDouble) map {
      a => {
        val xinit = if (a > 1.0) a else 1.0

        lazy val newtonRaphson : State[Double,Double] = for {
          xold <- State.get[Double]
          _ <- State.set[Double](xold - (xold*xold -a)/ (2.0*xold))
          xnew <- State.get[Double]
          result <- if ((xold - xnew) / xold < EPSILON)
          State.pure[Double, Double](xnew)
          else newtonRaphson
        } yield result

        newtonRaphson.runA(xinit).value
      }
    }

    js.Dynamic.global.document.write(answer.getOrElse("에러").asInstanceOf[scala.scalajs.js.Any])
  }
}
