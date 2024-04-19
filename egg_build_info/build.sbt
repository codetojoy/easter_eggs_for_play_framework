name := """play-java-forms-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
                                      .enablePlugins(BuildInfoPlugin)
                                      .settings(
                                        name := "sandbox",
                                        description := "my sandbox desc",
                                        version := "0.9.0",
                                        scalaVersion := "3.2.2",
                                        sbtVersion := "1.5.2",
                                        buildInfoKeys := Seq[BuildInfoKey](name, version),
                                        buildInfoPackage := "net.codetojoy"
                                      )


scalaVersion := "2.13.10"

(Test / testOptions) := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

libraryDependencies ++= Seq(
  guice,
  caffeine
)

// disabled until https://github.com/playframework/playframework/issues/9845 is solved
//scalacOptions ++= List("-encoding", "utf8", "-Xfatal-warnings", "-deprecation")
javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Werror"
) 
