lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """play-java-jpa-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      guice,
      javaJpa,
      "org.hibernate" % "hibernate-core" % "5.6.15.Final",
      javaWs % "test",
      "org.awaitility" % "awaitility" % "4.2.0" % "test",
      "org.assertj" % "assertj-core" % "3.24.2" % "test",
      "org.mockito" % "mockito-core" % "3.12.4" % "test",
      "org.postgresql" % "postgresql" % "42.5.2",
    ),
    Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v"),
    scalacOptions ++= List("-encoding", "utf8", "-deprecation", "-feature", "-unchecked"),
    javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation", "-Werror"),
    PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"
  )
