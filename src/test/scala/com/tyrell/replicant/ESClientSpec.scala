package com.tyrell.replicant

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.BeforeAndAfter
import org.scalatest.mockito.MockitoSugar
import org.apache.spark.sql.types.{StringType, StructField, StructType, _}
import org.apache.spark.sql._
import org.junit.Assert._
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import com.tyrell.replicant.utils.TestUtils.createSampleData

@RunWith(classOf[JUnitRunner])
class ESClientSpec extends FunSuite with BeforeAndAfter with MockitoSugar with BeforeAndAfterAll {

  var spark: SparkSession = _
  val columnName = "value"

  override def beforeAll() {
    val conf = new SparkConf().setAppName("Bill").setMaster("local[*]")
     spark = SparkSession
      .builder().config(conf)
      .appName("Spark Structured Streaming Example")
      .getOrCreate()
  }

  override def afterAll() {
  }

  test ("extract smoke test") {
    // given
    val fakeSpark = mock[SparkSession]
    val fakeSqlContext = mock[SQLContext]
    val fakeDataFrameReader = mock[DataFrameReader]

    // and
    Mockito.when(fakeSpark.sqlContext).thenReturn(fakeSqlContext)
    Mockito.when(fakeSqlContext.read).thenReturn(fakeDataFrameReader)
    Mockito.when(fakeDataFrameReader.schema(StructType(StructField(columnName, StringType, nullable = true) :: Nil))).thenReturn(fakeDataFrameReader)
    Mockito.when(fakeDataFrameReader.parquet("/tmp/json-sink")).thenReturn(createSampleData(spark, columnName))

    // and
    val underTest = new ESClient(fakeSpark)

    // when
    underTest.extract

    // then
    // nothing to do here!!
  }

  test ("transform") {
    // given
    val underTest = new ESClient(spark)
    val expected = "(hello, world)"

    // when
    val actual = underTest.transform(createSampleData(spark, columnName))

    // then
    assertEquals(expected,actual)
  }

  test ("upload smoke test") {
    // given
    val fakeSpark = mock[SparkSession]
    val fakeSparkContext = mock[SparkContext]
    val underTest = new ESClient(fakeSpark)

    // when
    Mockito.when(fakeSpark.sparkContext).thenReturn(fakeSparkContext)
    underTest.upload("cheese")

    // then
    //nothing to do here!!
  }

}