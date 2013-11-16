import AssemblyKeys._

name := "hello-finagle"

version := "0.1-SNAPSHOT"

organization := "com.dpederson"

scalaVersion := "2.10.2"

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.10.2")

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-feature", "-P:continuations:enable", "-language:higherKinds")

resolvers ++= Seq(
  "central.maven" at "http://central.maven.org/maven2",
  "twitter" at "http://maven.twttr.com/"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "scrooge-core" % "3.8.0",
  "org.apache.thrift" % "libthrift" % "0.9.1",
  "com.twitter" %% "finagle-thrift" % "6.5.2"
)

// Assembly settings
mainClass in Global := Some("com.dpederson.app.ServerApp")

assemblySettings
