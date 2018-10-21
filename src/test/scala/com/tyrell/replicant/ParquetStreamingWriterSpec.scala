package com.tyrell.replicant

import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}
import com.tyrell.replicant.utils.TestUtils.readParquetFile

@RunWith(classOf[JUnitRunner])
class ParquetStreamingWriterSpec extends FunSuite with BeforeAndAfter with MockitoSugar with BeforeAndAfterAll {

  var spark: SparkSession = _
  val columnName = "value"

  override def beforeAll() {
    val conf = new SparkConf().setAppName("App").setMaster("local[*]")
     spark = SparkSession
      .builder().config(conf)
      .appName("Spark Structured Streaming Example")
      .getOrCreate()
  }

  test ("writeDataToParquetFile smoke test") {
    // given
    val df = readParquetFile(spark)

    // and
    val underTest = new ParquetStreamingWriter(df, spark)

    // and
    val parquetWriterThread = new Thread(new Runnable {
      override def run(): Unit = {
        underTest.writeDataToParquetFile(df,spark)
      }
    })
    val timeout = 10000

    // when
    parquetWriterThread.start()
    Thread.sleep(timeout)
    parquetWriterThread.stop

    // then
    // nothing to do here!!
  }

}