package com.tyrell.replicant

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object App {

  val spark = getSparkSession()

  def main(args: Array[String]): Unit = {
    val data = KafkaClient(spark).readDataFromKafka
    writeDataToParquetFile(data, spark)
    uploadParquetFileToElasticSearch(spark)
  }

  private def getSparkSession(): SparkSession = {
    val conf = new SparkConf().setAppName("App")
      .setMaster("local[*]")
      .set("es.index.auto.create", "true")
      .set("es.mapping.id", "id")
      .set("es.nodes", "localhost")
      .set("es.port", "9200")
      .set("es.http.timeout", "5m")
      .set("es.scroll.size", "50")

    SparkSession
      .builder().config(conf)
      .appName("App")
      .getOrCreate()
  }

  private def uploadParquetFileToElasticSearch(spark : SparkSession): Unit = {
    new Thread(new ESClient(spark)).start
  }

  private def writeDataToParquetFile(data : DataFrame, spark : SparkSession): Unit = {
    new Thread(new ParquetStreamingWriter(data, spark)).start
  }

}