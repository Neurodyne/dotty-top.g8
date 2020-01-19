val dottyVersion    = "dotty_0.22:0.22.0-bin-20200118-754a552-NIGHTLY"
val scala212Version = "2.13.1"
val zioVersion      = "1.0.0-RC17"

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

lazy val catsDeps = libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core"   % "2.1.0-RC1",
  "org.typelevel" %% "cats-effect" % "2.0.0"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-top",
    version := "0.0.1",
    scalaVersion := dottyVersion,
    crossScalaVersions := Seq(dottyVersion, scala212Version),
    scalacOptions ++= Seq(
      "-noindent"
    ),
    zioDeps,
    catsDeps,
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(scalaVersion.value)),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
