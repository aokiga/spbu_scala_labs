name := "scala_labs"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0" withSources() withJavadoc()
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.3" withSources() withJavadoc()
scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds")