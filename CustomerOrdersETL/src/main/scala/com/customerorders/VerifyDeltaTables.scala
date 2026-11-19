package com.customerorders

import org.apache.spark.sql.SparkSession

object VerifyDeltaTables {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Verify Delta Tables")
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

      spark.sparkContext.setLogLevel("ERROR")

      val paths = Seq(
  "Bronze" -> "data/multi_file_output/bronze/customer_orders/",
  "Quarantine" -> "data/multi_file_output/quarantine/customer_orders/",
  "Silver" -> "data/multi_file_output/silver/customer_orders/"
)

      println("\n========== DELTA TABLE SUMMARY ==========")

      paths.foreach { case (name, path) =>

        val df = spark.read
          .format("delta")
          .load(path)

        println(s"\n$name")
        println(s"Record count: ${df.count()}")

        if (df.columns.contains("order_id")) {
          if (df.columns.contains("amount")) {
  		df.select("order_id", "amount")
    		.orderBy("order_id")
    		.show(false)
	} else {
  		df.select("order_id")
    		.orderBy("order_id")
    		.show(false)
}
        }
      }

    } finally {
      spark.stop()
    }
  }
}