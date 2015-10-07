name := "hello-finagle-thrift"
version := "0.2"
organization := "zdavep"
scalaVersion := "2.11.7"

val finagleVersion = "6.29.0"

resolvers += "twitter" at "https://maven.twttr.com/"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-thriftmux" % finagleVersion,
  "com.twitter" %% "finagle-serversets" % finagleVersion,
  "com.twitter" % "finagle-zipkin_2.11" % finagleVersion,
  "com.twitter" %% "scrooge-core" % "4.1.0",
  "org.apache.thrift" % "libthrift" % "0.9.2"
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Xlint:_",
  "-Xfuture",
  "-Ywarn-dead-code",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard"
)

mainClass in Global := Some("zdavep.hello.HelloServiceServer")

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

addCommandAlias("dist", ";clean;compile;scalastyle;assembly")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "com/twitter/common/args/apt/cmdline.arg.info.txt.1" => MergeStrategy.first
    case "org/slf4j/impl/StaticLoggerBinder.class" => MergeStrategy.first
    case "org/slf4j/impl/StaticMDCBinder.class" => MergeStrategy.first
    case "org/slf4j/impl/StaticMarkerBinder.class" => MergeStrategy.first
    case x => old(x)
  }
}

Revolver.settings
