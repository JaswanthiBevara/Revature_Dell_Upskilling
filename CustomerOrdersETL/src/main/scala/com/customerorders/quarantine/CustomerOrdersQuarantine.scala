package com.customerorders.quarantine

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object CustomerOrdersQuarantine {

  def prepareRejectedRecords(
      rejectedDF: DataFrame
  ): DataFrame = {

    rejectedDF
      .withColumn("_quarantined_at", current_timestamp())
      .withColumn(
        "_quarantine_reason",
        col("_validation_error")
      )
      .drop("_validation_error")
  }
}