name := """thread-pools-jdk25"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

crossScalaVersions := Seq("2.13.16", "3.3.6")
scalaVersion := crossScalaVersions.value.head

(Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

libraryDependencies ++= Seq(
  javaWs,
  guice
)

javacOptions ++= Seq(
  "--enable-preview", "--release", "25",
  "-Xlint:unchecked",
  "-Xlint:deprecation"
) 
