import AssemblyKeys._
import com.twitter.scrooge.ScroogeSBT

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
  "com.twitter" %% "scrooge-core" % "3.9.0",
  "org.apache.thrift" % "libthrift" % "0.9.1",
  "com.twitter" %% "finagle-thrift" % "6.6.2",
  "com.twitter" %% "finagle-serversets" % "6.6.2"
)

// Assembly settings
mainClass in Global := Some("com.dpederson.app.ServerApp")

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "com/twitter/common/args/apt/cmdline.arg.info.txt.1" => MergeStrategy.first
    case x => old(x)
  }
}

ScroogeSBT.newSettings
