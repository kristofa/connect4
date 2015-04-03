import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "connect4"

version := "0.1-SNAPSHOT"

organization := "com.github.kristofa"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2-core" % "2.4.15" % "test"
)

mainClass in Compile := Some("com.github.kristofa.connect4.Connect4")
