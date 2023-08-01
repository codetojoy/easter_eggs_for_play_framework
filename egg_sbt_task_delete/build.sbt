lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .settings(
    name := "play-java-ebean-example",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.postgresql" % "postgresql" % "42.5.2",
      "org.awaitility" % "awaitility" % "3.1.6" % Test,
      "org.assertj" % "assertj-core" % "3.12.2" % Test,
      "org.mockito" % "mockito-core" % "3.0.0" % Test,
    ),
    (Test / testOptions) += Tests.Argument(TestFrameworks.JUnit, "-a", "-v"),
    javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")
  )

import scala.reflect.io.Directory
import java.io.File

lazy val prepForDocker = taskKey[Unit]("prepares for docker")

prepForDocker := { 
    println("running prepForDocker task")
    val dir = new Directory(new File("./conf/dev_resources"))
    dir.deleteRecursively()
    println("running prepForDocker done")
}
