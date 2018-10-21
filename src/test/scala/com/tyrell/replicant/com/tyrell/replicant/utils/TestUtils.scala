package com.tyrell.replicant.com.tyrell.replicant.utils

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object TestUtils {

  //infix notation
  def matches(content : String) = content contains "hello, world"

  def createSampleData(spark : SparkSession, columnName: String):  DataFrame = {
    val sampleData = Array(
      Array("hello"),
      Array("world")
    )
    val rddOfRows=spark.sparkContext.parallelize(sampleData).map(f=>Row.fromSeq(f.toSeq))
    val sampleDataWithColumnNames=new Array[StructField](1)
    sampleDataWithColumnNames(0)=StructField(columnName,StringType)
    spark.sqlContext.createDataFrame(rddOfRows,StructType(sampleDataWithColumnNames.toList))
  }

  def readParquetFile(spark : SparkSession) : DataFrame = {
    val sampleParquetFileLocation = "src/test/resources/sample_parquet_files"
    import org.apache.spark.sql.types._
    val schema = StructType(StructField("value", StringType, nullable = true) :: Nil)
    val df = spark.sqlContext.readStream.schema(schema).parquet(sampleParquetFileLocation)
    df
  }

}
