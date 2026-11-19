
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object LinkedInMemberAnalytics {

  def main(args: Array[String]): Unit = {

    // 1. Create Spark Session
    val spark = SparkSession.builder()
      .appName("LinkedIn Member Interaction Analytics")
      .master("local[*]")
      .config("spark.sql.shuffle.partitions", "3")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    // 2. Define JSON schema
    val eventSchema = StructType(Seq(
      StructField("eventId", StringType, true),
      StructField("memberId", StringType, true),
      StructField("postId", StringType, true),
      StructField("eventType", StringType, true),
      StructField("eventTime", TimestampType, true)
    ))

    // 3. Read streaming data from Kafka
    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9093")
      .option("subscribe", "linkedin-events")
      .option("startingOffsets", "earliest")
      .load()

    // 4. Convert Kafka value (binary) into String
    val jsonDF = kafkaDF
      .selectExpr("CAST(value AS STRING) AS json_value")

    // 5. Parse JSON into structured columns
    val parsedDF = jsonDF
      .select(
        from_json(col("json_value"), eventSchema).alias("event")
      )
      .select("event.*")
val watermarkedDF = parsedDF
  .withWatermark("eventTime", "2 minutes")

   // 6. Calculate post-wise interaction analytics
val postAnalyticsDF = parsedDF
  .groupBy("postId")
  .agg(
    sum(when(col("eventType") === "view", 1).otherwise(0))
      .alias("totalViews"),

    sum(when(col("eventType") === "like", 1).otherwise(0))
      .alias("totalLikes"),

    sum(when(col("eventType") === "comment", 1).otherwise(0))
      .alias("totalComments"),

    size(collect_set(col("memberId")))
      .alias("uniqueMembers")
  )

// 7. Display updated post-wise analytics
val query = postAnalyticsDF.writeStream
  .outputMode("complete")
  .format("console")
  .option("truncate", "false")
  .option("numRows", "50")
  .option("checkpointLocation", "checkpoints/post-wise")
  .start()

// 9. Calculate one-minute window-wise analytics
val windowAnalyticsDF = watermarkedDF
  .groupBy(
    window(col("eventTime"), "1 minute"),
    col("postId")
  )
  .agg(
    sum(when(col("eventType") === "view", 1).otherwise(0))
      .alias("totalViews"),

    sum(when(col("eventType") === "like", 1).otherwise(0))
      .alias("totalLikes"),

    sum(when(col("eventType") === "comment", 1).otherwise(0))
      .alias("totalComments")
  )

// 10. Display window-wise analytics
val windowQuery = windowAnalyticsDF.writeStream
  .outputMode("complete")
  .format("console")
  .option("truncate", "false")
  .option("numRows", "50")
  .option("checkpointLocation", "checkpoints/window-wise")
  .start()

// 11. Keep both streaming queries running
spark.streams.awaitAnyTermination()

  }
}
