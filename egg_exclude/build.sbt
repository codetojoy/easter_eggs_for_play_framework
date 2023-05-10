name := """egg-exclude"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
                                      .enablePlugins(SbtWeb)

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

pipelineStages := Seq(filter)

// includeFilter in filter ++= Seq("SpecRunner.html", "*Spec.js")

// includeFilter in filter := com.slidingautonomy.sbt.filter.SbtFilter.autoImport.PathFilter((sourceDirectory in Assets).value / "javascripts" / "tests")
// (new java.io.File("public/javascripts/tests"))


