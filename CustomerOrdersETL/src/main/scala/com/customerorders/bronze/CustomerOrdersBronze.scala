
package com.customerorders.bronze

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object CustomerOrdersBronze {

  def addMetadata(df: DataFrame): DataFrame = {

    df
      .withColumn("_source_file", input_file_name())
      .withColumn("_ingested_at", current_timestamp())
      .withColumn(
        "_record_hash",
        sha2(to_json(struct(df.columns.map(col): _*)), 256)
      )
  }
}
