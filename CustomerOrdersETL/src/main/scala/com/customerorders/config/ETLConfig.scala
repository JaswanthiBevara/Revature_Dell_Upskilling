package com.customerorders.config

object ETLConfig {
val BasePath =
  "abfss://adlsdatabrics2c@adlsdatabricks2.dfs.core.windows.net/"

	val LandingPath = BasePath + "landing/customer_orders/"
	val BronzePath = BasePath + "bronze/customer_orders/"
	val QuarantinePath = BasePath + "quarantine/customer_orders/"
	val SilverPath = BasePath + "silver/customer_orders/"

  val SchemaCheckpointPath =
    BasePath + "checkpoints/customer_orders/schema/"

  val StreamCheckpointPath =
    BasePath + "checkpoints/customer_orders/stream/"
}