package ML.KMns

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
// $example on$
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
// $example off$

object KMns {

  def main(args: Array[String]) {

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("KMeansExample").setMaster("local[2]")
    val sc = new SparkContext(conf)

    // $example on$
    // Load and parse the data

    val pattern = "\\d+\\:".r

    val data = sc.textFile("D:\\Java\\code\\Wordcount\\src\\main\\stabilityselection\\stability_selection_example\\data\\rdf1NO.csv")
    val parsedData = data.map(s => Vectors.dense(
      s.split(",").map(str => pattern.replaceFirstIn(str,""))//去掉 1:  2:
        .map(_.toDouble))
    ).cache().filter(arr => arr(0) > 10 && arr(0) < 40)//筛选数据


    // Cluster the data into two classes using KMeans
    val numClusters = 1
    val numIterations = 400
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsedData)
    println(s"Within Set Sum of Squared Errors = $WSSSE")

    println("Cluster centers:")

    for (c <- clusters.clusterCenters) {

      println("  " + c.toString)

    }

    // Save and load model
    //clusters.save(sc, "target/tmp/KMeansModel33")
    //val sameModel = KMeansModel.load(sc, "target/tmp/KMeansModel")
    // $example off$

    sc.stop()
  }

}
