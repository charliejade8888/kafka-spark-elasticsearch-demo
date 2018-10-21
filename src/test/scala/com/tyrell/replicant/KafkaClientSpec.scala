package com.tyrell.replicant

import com.tyrell.replicant.utils.TestUtils.createSampleData
import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.scalatest.junit.JUnitRunner
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}

@RunWith(classOf[JUnitRunner])
class KafkaClientSpec extends FunSuite with BeforeAndAfter with MockitoSugar with BeforeAndAfterAll {

  test ("readDataFromKafka exists") {
    // given
    val fakeSpark = mock[SparkSession]
    val r = new Runnable { override def run(): Unit = { KafkaClient(fakeSpark).readDataFromKafka } }

    // when
    try { new Thread(r).start()} catch { case e: Exception => }

    // then
    // nothing to do here!!
  }

  test ("printDFToConsole exists") {
    // given
    val fakeSpark = mock[SparkSession]
    val r = new Runnable { override def run(): Unit = { KafkaClient(fakeSpark).printDFToConsole(createSampleData(fakeSpark, "value")) } }

    // when
    try { new Thread(r).start()} catch { case e: Exception => }

    // then
    // nothing to do here!!
  }

}