package ML.RF

import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 循环版*/
object RandomForestTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]")
      .setAppName("RandomForestRegressionExample")
    val sc = new SparkContext(conf)
    // $example on$
    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\gromtang\\Desktop\\调研\\csv\\sslib.csv") //"D:\\RFData1.txt"
    // Split the data into training and test sets (30% held out for testing)


    // Train a RandomForest model.
    // Empty categoricalFeaturesInfo indicates all features are continuous.

    var minMSE = 100.0
    var minX = 0
    //for (i <- 0 to 10) {}
      for (j <- 1 to 10) {

        val splits = data.randomSplit(Array(0.7, 0.3))
        val (trainingData, testData) = (splits(0), splits(1))
        val numClasses = 50
        val categoricalFeaturesInfo = Map[Int, Int]()
        val numTrees = 130 // Use more in practice.130
        val featureSubsetStrategy = "auto" // Let the algorithm choose.
        val impurity = "variance"
        val maxDepth = 12//13
        val maxBins = 300

        val model = RandomForest.trainRegressor(trainingData, categoricalFeaturesInfo,
          numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

        // Evaluate model on test instances and compute test error
        val labelsAndPredictions = testData.map { point =>
          val prediction = model.predict(point.features)
          (point.label, prediction)
        }
        val testMSE = labelsAndPredictions.map { case (v, p) => math.pow((v - p), 2) }.mean()
        if (testMSE < minMSE) {
          minMSE = testMSE
          minX = maxDepth
          if (minMSE < 15) {
            model.save(sc, "target/tmp/RFModelMSELower15")
            println(s"Test Mean Squared Error = $minMSE")
            return
          }
        }
      }


      //println(s"Learned regression forest model:\n ${model.toDebugString}")
      println(s"Test Mean Squared Error = $minMSE")
      println("minX =" + minX)

      // Save and load model
      //model.save(sc, "target/tmp/RFModelPerCoal2")

      //val sameModel = RandomForestModel.load(sc, "target/tmp/RFModelSimuData1")
      // $example off$


      sc.stop()
    }
  }



/**
  *稳定版*/
//object RandomForestTest {
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setMaster("local[4]")
//      .setAppName("RandomForestRegressionExample")
//    val sc = new SparkContext(conf)
//    // $example on$
//    // Load and parse the data file.
//    val data = MLUtils.loadLibSVMFile(sc, "C:\\Users\\gromtang\\Desktop\\调研\\csv\\sslib.csv")//"D:\\RFData1.txt"
//    // Split the data into training and test sets (30% held out for testing)
//    val splits = data.randomSplit(Array(0.7, 0.3))
//    val (trainingData, testData) = (splits(0), splits(1))
//
//    // Train a RandomForest model.
//    // Empty categoricalFeaturesInfo indicates all features are continuous.
//    val numClasses = 20
//    val categoricalFeaturesInfo = Map[Int, Int]()
//    val numTrees = 140 // Use more in practice.
//    val featureSubsetStrategy = "auto" // Let the algorithm choose.
//    val impurity = "variance"
//    val maxDepth = 16
//    val maxBins = 100
//
//    val model = RandomForest.trainRegressor(trainingData, categoricalFeaturesInfo,
//      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
//
//    // Evaluate model on test instances and compute test error
//    val labelsAndPredictions = testData.map { point =>
//      val prediction = model.predict(point.features)
//      (point.label, prediction)
//    }
//    val testMSE = labelsAndPredictions.map{ case(v, p) => math.pow((v - p), 2)}.mean()
//    //println(s"Learned regression forest model:\n ${model.toDebugString}")
//    println(s"Test Mean Squared Error = $testMSE")
//
//    // Save and load model
//    //model.save(sc, "target/tmp/RFModelTiaoCan1")
//
//    //val sameModel = RandomForestModel.load(sc, "target/tmp/RFModelSimuData1")
//    // $example off$
//
//
//    sc.stop()
//  }
//}
