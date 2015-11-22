name := """sherpa"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",

  specs2 % Test
  // "org.specs2" %% "specs2" % "3.3.1" % "test",
  // "org.specs2" %% "specs2-matcher" % "3.6.5" % "test",
  // "org.specs2" %% "specs2-matcher-extra" % "3.6.5" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := false