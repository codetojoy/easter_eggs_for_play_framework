lazy val scala213 = "2.13.12"
lazy val scala3 = "3.3.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := "play-java-rest-api-example",
    version := "1.0-SNAPSHOT",
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213, scala3),
    libraryDependencies ++= Seq(
      guice,
      javaJpa,
      "org.postgresql" % "postgresql" % "42.6.0",
      "org.hibernate" % "hibernate-core" % "6.3.1.Final",
      "io.dropwizard.metrics" % "metrics-core" % "4.2.21",
      "com.palominolabs.http" % "url-builder" % "1.1.5",
      "net.jodah" % "failsafe" % "2.4.4",
    ),
    PlayKeys.externalizeResources := false,
    (Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v")),
    javacOptions ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    )
  )

