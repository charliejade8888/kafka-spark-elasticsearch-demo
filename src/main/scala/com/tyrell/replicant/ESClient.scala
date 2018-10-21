package com.tyrell.replicant

import org.apache.spark.sql.{DataFrame, SparkSession}

class ESClient(spark: SparkSession) extends Runnable {
  import org.elasticsearch.spark._
  val delay = 15000
  def run {
    import java.nio.file.{Files, Paths}
    while (true) {
      Thread.sleep(delay)
      if (Files.exists(Paths.get("/tmp/json-sink"))) {
        upload(transform(extract))
      }
    }
  }

  def extract() : DataFrame = {
    import org.apache.spark.sql.types._
    val schema = StructType(StructField("value", StringType, nullable = true) :: Nil)
    val df = spark.sqlContext.read.schema(schema).parquet("/tmp/json-sink")
    println("DataFrame::")
    df.show
    df
  }

  def transform(df : DataFrame) : String = {
    import spark.implicits._
    val values = df.selectExpr("CAST(value AS STRING)").as[String]
    var value = values.select("value").collect().map(_(0)).toList.toString
    value = value.replaceAll("List", "")
    value
  }

  def upload(uploadMe : String) : Unit = {
    val sc = spark.sparkContext
    sc.makeRDD(Seq(Map("key" -> uploadMe, "id" -> "spud"))).saveToEs("mylovelyindex/test")
  }

  def read(): Unit = {
    val sc = spark.sparkContext
    val rdd = sc.esRDD("mylovelyindex/test")
    println(rdd.first().toString())
  }

}