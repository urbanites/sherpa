name := """sherpa"""

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.0-M1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")


fork in run := true