import AssemblyKeys._
import com.twitter.scrooge.ScroogeSBT

name := "hello-finagle"

version := "0.1-SNAPSHOT"

organization := "zdavep"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:higherKinds")

resolvers ++= Seq(
  "central.maven" at "http://central.maven.org/maven2",
  "twitter" at "http://maven.twttr.com/"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "scrooge-core" % "3.17.0",
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "com.twitter" %% "finagle-thrift" % "6.22.0",
  "com.twitter" %% "finagle-serversets" % "6.22.0"
)

// Assembly settings
mainClass in Global := Some("zdavep.app.ServerApp")

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "com/twitter/common/args/apt/cmdline.arg.info.txt.1" => MergeStrategy.first
    case x => old(x)
  }
}

ScroogeSBT.newSettings
