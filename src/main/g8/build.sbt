val dottyVersion    = "0.22.0-bin-20191220-d45fea0-NIGHTLY"
val scala212Version = "2.13.1"
val zioVersion      = "1.0.0-R17"

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio"          % zioVersion,
  "dev.zio" %% "zio-test"     % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-top",
    version := "0.0.1",
    // To make the default compiler and REPL use Dotty
    scalaVersion := dottyVersion,
    // To cross compile with Dotty and Scala 2
    crossScalaVersions := Seq(dottyVersion, scala212Version)
    //zioDeps,
    //testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  ) //

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
