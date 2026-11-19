package com.customerorders.ingestion

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types._

object CustomerOrdersIngestion {

  val orderSchema: StructType = StructType(Seq(
    StructField("order_id", StringType, true),
    StructField("customer_id", StringType, true),
    StructField("order_date", StringType, true),
    StructField("amount", StringType, true),
    StructField("operation", StringType, true),
    StructField("source_updated_at", StringType, true)
  ))

  def readCsv(
      spark: SparkSession,
      inputPath: String
  ): DataFrame = {

    spark.read
      .format("csv")
      .option("header", "true")
      .option("mode", "PERMISSIVE")
      .option("columnNameOfCorruptRecord", "_corrupt_record")
      .schema(orderSchema.add(
        StructField("_corrupt_record", StringType, true)
      ))
      .load(inputPath)
  }
}