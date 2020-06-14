package Streaming.RecyclingStation

import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object StreamTextFile {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf()
      .setMaster("spark://linux01:7077")//local[2]spark://192.168.44.30:7077
      .setAppName("FlumePullApp")
      .setJars(Array("D:\\Java\\code\\Wordcount\\target\\Wordcount-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.121.1")
      .set("spark.executor.memory", "512m")
      .set("spark.executor.core", "2")

    val sc = new SparkContext(conf)

    //val ssc = new StreamingContext(conf, Seconds(10))
    val ssc = new StreamingContext(sc, Seconds(10))

    val lines = ssc.textFileStream("hdfs://linux01:8020/AAA/")
    val words = lines.flatMap(_.split(" ")).map(word => (word, 1)).reduceByKey(_+_)//关键点
    lines.print()
    //wordCounts.saveAsTextFiles("hdfs://linux01:8020/FlumeSpark")//要权限

    ssc.start()
    ssc.awaitTermination()
  }
}
