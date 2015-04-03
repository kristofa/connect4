import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "connect4"

version := "0.1-SNAPSHOT"

organization := "com.github.kristofa"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.11" % "test",
        "com.novocode" % "junit-interface" % "0.11" % "test",
        "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

mainClass in Compile := Some("com.github.kristofa.connect4.Connect4")
