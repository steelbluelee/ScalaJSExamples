enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "ScalaJSExamples"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.lihaoyi" %%% "scalatags" % "0.6.7",
  "org.typelevel" %%% "cats-core" % "1.0.1"
)

scalacOptions += "-Ypartial-unification"

scalacOptions += "-language:higherKinds"
