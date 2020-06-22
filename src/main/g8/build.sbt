val dottyVersion    = "0.26.0-bin-20200618-de75713-NIGHTLY"
val scala212Version = "2.12.11"
val scala213Version = "2.13.2"
val zioVersion      = "1.0.0-RC21"

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
    scalaVersion := dottyVersion,
    crossScalaVersions := Seq(dottyVersion, scala212Version, scala213Version),
    scalacOptions ++= Seq(
      "-noindent"
    ),
    zioDeps,
    libraryDependencies := libraryDependencies.value.map(_.withDottyCompat(scalaVersion.value)),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
