package Streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.model.RandomForestModel

object Evaluation {
  def main(args: Array[String]): Unit = {
    //思路1:用优选数据算基准值，用全体数据求模型
    //思路2:分别训练有无使用垃圾焚烧的数据
    val conf = new SparkConf()
      .setAppName("Evaluation33")
      .setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rfModel = RandomForestModel.load(sc,"target/tmp/RFModelPerCoal1")

    val data = Array[Double](8.71,73.8,9.12,0.07,11.52,7.78,658.2,0.01,1033,410,57,-96,46,941,-40,81,80,9.12,32,0.07,-3764,363,790,836,-1496,-540,872,-316,854,45,11.9,2,6139,1239)
    val dataCurrent = Vectors.dense(data)
    val dataStandard = Array[Double](6.406,93.290,9.567,0.109,9.828,6.806,642.419,0.044,1076.711,652.167,67.521,-141.345,49.734,1039.845,-50.724,67.416,66.210,9.569,59.581,0.109,-4778.087,339.740,798.816,846.794,-1788.157,-818.109,869.214,-457.259,946.505,54.724,10.761,3.178,6687.720,2766.617)//基准值集合
    val resultStandard = 110.064
    //全体数据的基准值110.064 (6.406,93.290,9.567,0.109,9.828,6.806,642.419,0.044,1076.711,652.167,67.521,-141.345,49.734,1039.845,-50.724,67.416,66.210,9.569,59.581,0.109,-4778.087,339.740,798.816,846.794,-1788.157,-818.109,869.214,-457.259,946.505,54.724,10.761,3.178,6687.720,2766.617)

    val resultCurrent = rfModel.predict(dataCurrent)
    val resultFinal = for(i <- 0 to dataStandard.length - 1) yield {
      data(i) = dataStandard(i)
      val resultSwitched = rfModel.predict(Vectors.dense(data))
      val iterPer :Double = (resultCurrent - resultSwitched) / (resultCurrent - resultStandard)
      println(i + 1, resultCurrent - resultSwitched, (iterPer * 100).toString.substring(0,5))
    }
    print("总偏差为:" + resultCurrent + "-" + resultStandard + "=" + (resultCurrent - resultStandard))
  }
}
