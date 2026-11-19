package com.customerorders.silver

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object CustomerOrdersSilver {

  def transform(validDF: DataFrame): DataFrame = {

    val typedDF = validDF
      .withColumn("amount", col("amount").cast("decimal(18,2)"))
      .withColumn("order_date", to_timestamp(col("order_date")))
      .withColumn("source_updated_at", to_timestamp(col("source_updated_at")))
      .withColumn("operation", upper(trim(col("operation"))))

    val windowSpec = Window
      .partitionBy("order_id")
      .orderBy(col("source_updated_at").desc)

    typedDF
      .withColumn("_row_number", row_number().over(windowSpec))
      .filter(col("_row_number") === 1)
	.drop(
  		"_row_number",
  	"_corrupt_record",
  	"_validation_error"
)
  }
}