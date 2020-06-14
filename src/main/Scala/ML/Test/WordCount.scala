package ML.Test

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  //private[Calculating] val wc = 1

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("spark://linux01:7077")
      .setAppName("WordCount")
      .setJars(Array("D:\\Java\\code\\Wordcount\\target\\Wordcount-1.0-SNAPSHOT.jar"))
      //.setIfMissing("spark.driver.host", "192.168.121.1")


    //实例化SparkContext
    val sc = new SparkContext(conf)

    //读取文件并进行单词统计
    sc.textFile("hdfs://linux01:8020/words.txt")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _, 1)
      .sortBy(_._2, false)
      .saveAsTextFile("hdfs://linux01:8020/WordCountResult/Test3") //WordCountResult

    sc.stop()
  }

}
