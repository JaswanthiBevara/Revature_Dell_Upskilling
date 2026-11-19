
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "CustomerOrdersETL",

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "4.1.0" % Provided,
      "io.delta" %% "delta-spark_4.1" % "4.2.0" % Provided,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    ),

    Test / parallelExecution := false
  )
