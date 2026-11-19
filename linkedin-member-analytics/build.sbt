
ThisBuild / version := "1.0.0"

ThisBuild / scalaVersion := "2.12.18"

ThisBuild / organization := "com.linkedin.analytics"

lazy val root = (project in file("."))
  .settings(
    name := "linkedin-member-analytics",

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.5.0",
      "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.0"
    ),

    Compile / run / fork := true,

    Compile / run / javaOptions ++= Seq(
  	"--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
  	"--add-opens=java.base/java.nio=ALL-UNNAMED",
  	"--add-opens=java.base/java.lang=ALL-UNNAMED",
  	"--add-opens=java.base/java.util=ALL-UNNAMED",
  	"--add-opens=java.base/java.io=ALL-UNNAMED",
  	"-Djava.library.path=C:\\Hadoop\\hadoop-3.3.5\\bin"
)
  )
