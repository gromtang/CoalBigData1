package ML.Test

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

object KmsTest {
    //wc 这里可以用private[Calculating]的wc

    def main(args: Array[String]) {

      // 屏蔽不必要的日志显示在终端上

      Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

      Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)



      // 设置运行环境

      val conf = new SparkConf()
        .setAppName("KmeansTest")
        .setMaster("spark://linux01:7077")//"spark://linux01:7077"
        .setJars(Array("D:\\Java\\code\\Wordcount\\target\\Wordcount-1.0-SNAPSHOT.jar"))
        .setIfMissing("spark.driver.host","192.168.121.1")


      //实例化SparkContext
      val sc = new SparkContext(conf)
      //val ssc = new StreamingContext(conf,Seconds(1))

      // 装载数据集

      //val data = ssc.socketTextStream("linux01",33333)

      val data = sc.textFile("hdfs://linux01:8020/input/kmeans_data.txt")

      val parsedData = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble)))



      // 将数据集聚类，2个类，20次迭代，进行模型训练形成数据模型

      val numClusters = 2

      val numIterations = 20

      val model = KMeans.train(parsedData, numClusters, numIterations)


      // 打印数据模型的中心点

      println("Cluster centers:")

      for (c <- model.clusterCenters) {

        println("  " + c.toString)

      }



      // 使用误差平方之和来评估数据模型

      val cost = model.computeCost(parsedData)

      println("Within Set Sum of Squared Errors = " + cost)



      // 使用模型测试单点数据

      println("Vectors 0.2 0.2 0.2 is belongs to clusters:" + model.predict(Vectors.dense("0.2 0.2 0.2".split(' ').map(_.toDouble))))

      println("Vectors 0.25 0.25 0.25 is belongs to clusters:" + model.predict(Vectors.dense("0.25 0.25 0.25".split(' ').map(_.toDouble))))

      println("Vectors 8 8 8 is belongs to clusters:" + model.predict(Vectors.dense("8 8 8".split(' ').map(_.toDouble))))



      // 交叉评估1，只返回结果

      val testdata = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble)))

      val result1 = model.predict(testdata)

      result1.saveAsTextFile("hdfs://linux01:8020/WordCountResult/KMeansFlumeTest4")



      // 交叉评估2，返回数据集和结果

      val result2 = data.map {

        line =>

          val linevectore = Vectors.dense(line.split(' ').map(_.toDouble))

          val prediction = model.predict(linevectore)

          line + " " + prediction

      }.saveAsTextFile("hdfs://linux01:8020/WordCountResult/KMeansFlumeTest5")



      sc.stop()

    }


}
