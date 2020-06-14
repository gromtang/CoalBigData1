package Streaming.RecyclingStation

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.flume.{FlumeUtils, SparkFlumeEvent}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

//todo:利用sparkStreaming对接flume数据，实现单词计数------Push推模式
object FlumeTest {
  def main(args: Array[String]): Unit = {
      val sparkConf: SparkConf = new SparkConf().setMaster("local[2]")//local[2]spark://linux01:7077
      .setAppName("FlumePullApp")
      .setJars(Array("D:\\Java\\code\\Wordcount\\target\\Wordcount-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.121.1")
      .set("spark.executor.memory", "512m")//似乎initial问题出在这里，必须是512，可能要和default env里设置的一样
      .set("spark.executor.core", "2")
      .set("spark.driver.memory", "512m")
      val sc = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
      //3、创建StreamingContext
      val ssc = new StreamingContext(sc,Seconds(5))
      //4、获取flume中的数据
      val stream: ReceiverInputDStream[SparkFlumeEvent] = FlumeUtils.createStream(ssc,"192.168.44.30",33332)
      //5、从Dstream中获取flume中的数据  {"header":xxxxx   "body":xxxxxx}
      val lineDstream: DStream[String] = stream.map(x => new String(x.event.getBody.array()))
      //6、切分每一行,每个单词计为1
      val wordAndOne: DStream[(String, Int)] = lineDstream.flatMap(_.split(" ")).map((_,1))
      //7、相同单词出现的次数累加
      val result: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)
      //8、打印输出
      result.print()
      //开启计算
      ssc.start()
      ssc.awaitTermination()
      }
  }
