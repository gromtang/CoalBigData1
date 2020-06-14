package Streaming

import DAO.WordCountHBaseDao
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object FKS_WordCount {
  def main(args: Array[String]): Unit = {


    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)


    if (args.length != 4){
      println("UsageL:Flume_Kafka_Streaming needs 4 args")
      System.exit(1)
    }

    val Array(zkQuorum,groupId,topics,numThreads) = args

    val conf = new SparkConf()
      .setAppName("Flume_Kafka_Streaming")
      .setMaster("local[2]")
      .set("spark.executor.memory", "512m")

    val ssc = new StreamingContext(conf,Seconds(5))
    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap

    val messages = KafkaUtils.createStream(ssc,zkQuorum,groupId,topicMap)

    //测试输出，打印输入次数
    //messages.map(_._2).count().print

    //写入hbase
    val messagesMap = messages.flatMap(_._2.split(" "))//_._2是数据
      .map((_, 1))
      .reduceByKey(_ + _)
      .foreachRDD(rdd => {
        rdd.foreachPartition(partitionRecords => {

          val list =  new ListBuffer[WordCountHBaseDao]
          partitionRecords.foreach(pair => {
            list.append(WordCountHBaseDao(pair._1,pair._2))
          })
          WordCountHBaseDao.save(list)

        })
      })


    ssc.start()
    ssc.awaitTermination()
  }
}
