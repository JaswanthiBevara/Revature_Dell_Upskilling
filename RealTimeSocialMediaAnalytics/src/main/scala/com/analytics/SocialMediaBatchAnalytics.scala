
package com.analytics

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SocialMediaBatchAnalytics {

  def main(args: Array[String]): Unit = {

    // 1. Create Spark Session
    val spark = SparkSession.builder()
      .appName("SocialMediaBatchAnalytics")
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

    // 2. Read Iceberg table
    val events = spark.table("local.social_media.events")

    println("\n========== TOTAL EVENTS ==========")
    println(s"Total events: ${events.count()}")

    // 3. Overall engagement metrics
    println("\n========== OVERALL ENGAGEMENT ==========")

    val overallMetrics = events.agg(
      sum("views").as("total_views"),
      sum("likes").as("total_likes"),
      sum("comments").as("total_comments"),
      count("*").as("total_events"),
      countDistinct("user_id").as("unique_users"),
      countDistinct("post_id").as("unique_posts")
    )

    overallMetrics.show(false)

    // 4. Post-wise engagement
    println("\n========== POST-WISE ENGAGEMENT ==========")

    val postMetrics = events
      .groupBy("post_id")
      .agg(
        sum("views").as("total_views"),
        sum("likes").as("total_likes"),
        sum("comments").as("total_comments"),
        count("*").as("total_events")
      )
      .withColumn(
        "total_engagement",
        col("total_likes") + col("total_comments")
      )
      .orderBy(desc("total_engagement"), desc("total_views"))

    postMetrics.show(false)
    // Save post-wise analytics into Iceberg
   postMetrics.writeTo(
     "local.social_media.post_engagement_summary"
    )
.using("iceberg")
.createOrReplace()

println("Post engagement summary saved to Iceberg!")

    // 5. Top-performing posts
    println("\n========== TOP PERFORMING POSTS ==========")

    postMetrics
      .orderBy(desc("total_engagement"))
      .limit(3)
      .show(false)

    // 6. Event-type distribution
    println("\n========== EVENT TYPE DISTRIBUTION ==========")

    events
      .groupBy("event_type")
      .agg(count("*").as("event_count"))
      .orderBy(desc("event_count"))
      .show(false)

    // 7. Engagement rate per post
    println("\n========== POST ENGAGEMENT RATE ==========")

    postMetrics
      .withColumn(
        "engagement_rate_percentage",
        when(
          col("total_views") > 0,
          round(
            (col("total_likes") + col("total_comments")) * 100.0 /
              col("total_views"),
            2
          )
        ).otherwise(0.0)
      )
      .select(
        "post_id",
        "total_views",
        "total_likes",
        "total_comments",
        "engagement_rate_percentage"
      )
      .show(false)

    spark.stop()
  }
}
