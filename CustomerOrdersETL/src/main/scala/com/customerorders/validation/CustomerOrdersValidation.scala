package com.customerorders.validation

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object CustomerOrdersValidation {

  def validate(df: DataFrame): DataFrame = {

    df
      .withColumn(
        "_validation_error",
        when(col("_corrupt_record").isNotNull, lit("MALFORMED_CSV"))
          .when(col("order_id").isNull || trim(col("order_id")) === "", lit("MISSING_ORDER_ID"))
          .when(col("customer_id").isNull || trim(col("customer_id")) === "", lit("MISSING_CUSTOMER_ID"))
          .when(
            col("amount").isNull ||
            !col("amount").rlike("^[0-9]+(\\.[0-9]{1,2})?$"),
            lit("INVALID_AMOUNT")
          )
          .when(col("amount").cast("decimal(18,2)") < 0, lit("NEGATIVE_AMOUNT"))
          .when(!upper(trim(col("operation"))).isin("I", "U", "D"), lit("INVALID_OPERATION"))
          .when(to_timestamp(col("order_date")).isNull, lit("INVALID_ORDER_DATE"))
          .when(to_timestamp(col("source_updated_at")).isNull, lit("INVALID_SOURCE_UPDATED_AT"))
          .otherwise(lit(null).cast("string"))
      )
  }

  def validRecords(df: DataFrame): DataFrame = {
    df.filter(col("_validation_error").isNull)
  }

  def rejectedRecords(df: DataFrame): DataFrame = {
    df.filter(col("_validation_error").isNotNull)
  }
}