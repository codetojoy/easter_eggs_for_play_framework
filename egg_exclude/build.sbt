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

// this works individually
// includeFilter in filter := "*Spec.js"
// includeFilter in filter := "SpecRunner.html"

includeFilter in filter := "*Spec.js" || "SpecRunner.html"

// doesn't work
// includeFilter in filter := "public/javascripts/tests"

// includeFilter in filter := com.slidingautonomy.sbt.filter.SbtFilter.autoImport.PathFilter((sourceDirectory in Assets).value / "javascripts" / "working")

// includeFilter in filter := "/Users/measter/src/github/codetojoy/easter_eggs_for_play_framework/egg_exclude/public/javascripts/tests"

// includeFilter in filter := "public/javascripts/tests"

