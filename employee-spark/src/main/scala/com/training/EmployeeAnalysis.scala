
package com.training

import org.apache.spark.sql.SparkSession

object EmployeeAnalysis {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Employee Analysis")
      .getOrCreate()

    try {
      import spark.implicits._

      val employees = Seq(
        (1, "John", 30000),
        (2, "David", 40000),
        (3, "Smith", 50000)
      ).toDF("id", "name", "salary")

      employees.show()

    } finally {
      spark.stop()
    }
  }
}
