package com.tyrell.replicant

import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}
import org.apache.spark.sql.{DataFrame, SparkSession}

class ParquetStreamingWriter(df : DataFrame, spark: SparkSession)  extends Runnable {

  def run {
    writeDataToParquetFile(df,spark)
  }

  def writeDataToParquetFile(df : DataFrame, spark : SparkSession): Unit = {
    import spark.implicits._
    val values = df.selectExpr("CAST(value AS STRING)").as[String]
    values.writeStream.
      format("parquet").
      option("path", "/tmp/json-sink").
      option("checkpointLocation", "/tmp/checkpoint")
      .outputMode(OutputMode.Append)
      .trigger(ProcessingTime("1 seconds")) //won't work is >1
      .start().awaitTermination()
  }

}