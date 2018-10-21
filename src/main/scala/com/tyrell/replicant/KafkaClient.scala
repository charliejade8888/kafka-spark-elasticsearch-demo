package com.tyrell.replicant

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.{OutputMode, ProcessingTime}

class KafkaClient(spark : SparkSession) {

  def readDataFromKafka : DataFrame = {
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "spark_sql_test_topic")
      .load()
    df
  }

  def printDFToConsole(df : DataFrame): Unit = {
        df.writeStream
          .trigger(ProcessingTime("5 seconds"))
          .outputMode("append")
          .format("console")
          .start()
          .awaitTermination()
  }

}

// companion object allows class to be instantiated without new keyword
object KafkaClient {
  def apply(spark :SparkSession): KafkaClient = new KafkaClient(spark)
}