package Streaming.RecyclingStation

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FlumeTest2 {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf()
      //.setMaster("spark://linux01:7077")//local[2]spark://linux01:7077
      .setAppName("FlumePullApp")
      //.setJars(Array("D:\\Java\\code\\Wordcount\\target\\Wordcount-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.121.1")
      //.set("spark.executor.memory", "512m")//似乎initial问题出在这里，必须是512，可能要和default env里设置的一样
      //.set("spark.executor.core", "2")
      //.set("spark.driver.memory", "512m")
      //.set("spark.cores.max", "2");

    //val sc = new SparkContext(conf) cores调整！！

    //val ssc = new StreamingContext(conf, Seconds(10))
    val ssc = new StreamingContext(conf, Seconds(10))

    val lines = FlumeUtils.createPollingStream(ssc, "192.168.1.20", 33333,StorageLevel.MEMORY_AND_DISK_2)
    val words = lines.map(x => new String(x.event.getBody.array()).trim).cache()
      .flatMap(_.split(" "))                                        //关键点
    val pairs = words.map(word => (word,1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    //wordCounts.saveAsTextFiles("hdfs://linux01:8020/AAA/","hadoop")//要权限

    ssc.start()
    ssc.awaitTermination()
  }
}
