package com.customerorders

import org.apache.spark.sql.SparkSession
import io.delta.tables.DeltaTable

object MigrateSilverSchema {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Migrate Silver Schema")
      .master("local[*]")
      .config(
        "spark.sql.extensions",
        "io.delta.sql.DeltaSparkSessionExtension"
      )
      .config(
        "spark.sql.catalog.spark_catalog",
        "org.apache.spark.sql.delta.catalog.DeltaCatalog"
      )
      .getOrCreate()

    try {

      val silverPath = "data/silver/customer_orders/"

      val silverDF = spark.read
        .format("delta")
        .load(silverPath)

      println("BEFORE MIGRATION")
      println(s"Count: ${silverDF.count()}")
      silverDF.printSchema()

      val cleanedDF = silverDF.drop("_validation_error")

      cleanedDF.write
        .format("delta")
        .mode("overwrite")
        .option("overwriteSchema", "true")
        .save(silverPath)

      println("AFTER MIGRATION")

      val updatedDF = spark.read
        .format("delta")
        .load(silverPath)

      println(s"Count: ${updatedDF.count()}")
      updatedDF.printSchema()

    } finally {
      spark.stop()
    }
  }
}