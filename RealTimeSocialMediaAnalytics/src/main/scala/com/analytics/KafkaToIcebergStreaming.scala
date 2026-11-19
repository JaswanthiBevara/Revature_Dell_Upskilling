
package com.analytics

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.streaming.Trigger

object KafkaToIcebergStreaming {

  def main(args: Array[String]): Unit = {

    // 1. Create Spark Session
    System.setProperty("hadoop.home.dir", "C:/Hadoop/hadoop-3.3.5")

    val spark = SparkSession.builder()
      .appName("RealTimeSocialMediaAnalytics")
      .master("local[*]")
      .config(
        "spark.sql.extensions",
        "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions"
      )
      .config(
        "spark.sql.catalog.local",
        "org.apache.iceberg.spark.SparkCatalog"
      )
      .config("spark.sql.catalog.local.type", "hadoop")
      .config(
        "spark.sql.catalog.local.warehouse",
        "file:///C:/iceberg/assignment-warehouse"
      )
      .config("spark.sql.shuffle.partitions", "2")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    // 2. Create Iceberg namespace
    spark.sql("CREATE NAMESPACE IF NOT EXISTS local.social_media")

    // 3. Create Iceberg table
    spark.sql("""
      CREATE TABLE IF NOT EXISTS local.social_media.events (
        event_id STRING,
        post_id STRING,
        user_id STRING,
        event_type STRING,
        event_time STRING,
        views BIGINT,
        likes BIGINT,
        comments BIGINT,
        event_timestamp TIMESTAMP,
        ingested_at TIMESTAMP
      ) USING iceberg
    """)

    // 4. Define JSON schema
    val eventSchema = new StructType()
      .add("event_id", StringType)
      .add("post_id", StringType)
      .add("user_id", StringType)
      .add("event_type", StringType)
      .add("event_time", StringType)
      .add("views", LongType)
      .add("likes", LongType)
      .add("comments", LongType)

    // 5. Read streaming data from Kafka
    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "social-media-assignment-events")
      .option("startingOffsets", "earliest")
      .option("failOnDataLoss", "false")
      .load()

    // 6. Parse JSON events
    val parsedDF = kafkaDF
      .selectExpr("CAST(value AS STRING) AS json_data")
      .select(from_json(col("json_data"), eventSchema).as("data"))
      .select("data.*")
      .withColumn(
        "event_timestamp",
        to_timestamp(col("event_time"), "yyyy-MM-dd HH:mm:ss")
      )
      .withColumn("ingested_at", current_timestamp())

    // 7. Validate events
    val validDF = parsedDF
      .filter(col("event_id").isNotNull)
      .filter(col("post_id").isNotNull)
      .filter(col("user_id").isNotNull)
      .filter(col("event_type").isin("VIEW", "LIKE", "COMMENT"))
      .filter(col("event_timestamp").isNotNull)

    // 8. Write events into Iceberg
    val query = validDF.writeStream
      .format("iceberg")
      .outputMode("append")
      .option(
        "checkpointLocation",
        "file:///C:/iceberg/assignment-checkpoints/social-media-events"
      )
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .toTable("local.social_media.events")

    // 9. Keep streaming application running
    query.awaitTermination()

    spark.stop()
  }
}
