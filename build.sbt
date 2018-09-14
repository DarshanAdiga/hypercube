
name := "hypercube"

version := "1.0"

scalaVersion := "2.11.4"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-remote" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.17"
)

mainClass in Compile := Some("org.adiga,hypercube.master.BaseMaster")
//mainClass in Compile := Some("org.adiga,hypercube.worker.BaseWorker")

assemblyJarName in assembly := "hypercube-master.jar"
//assemblyJarName in assembly := "hypercube-worker.jar"