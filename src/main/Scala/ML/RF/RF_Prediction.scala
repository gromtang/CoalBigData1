package ML.RF

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

object RF_Prediction {
  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[2]")
      .setAppName("RandomForestRegressionExample")
    val sc = new SparkContext(conf)
    /**
      * 模拟对新数据进行预测1
      * "D:\\Java\\code\\Wordcount\\src\\main\\stabilityselection\\stability_selection_example\\data\\sstest.csv"
      * "C:\\Users\\gromtang\\Desktop\\调研\\csv\\吨煤耗.csv"
      */
    val rawData = MLUtils.loadLibSVMFile(sc,"C:\\Users\\gromtang\\Desktop\\调研\\csv\\sslib500.csv")

    //读取模型
    val rfModel = RandomForestModel.load(sc,"target/tmp/RFModelMSELower18")
    //进行预测

    val dataAndPreLable = rawData.map { point =>
        val prediction = rfModel.predict(point.features)
        //(point.label, prediction)
        //Math.abs(prediction - point.label)
      (prediction - point.label)*(prediction - point.label)
    }
    //dataAndPreLable.foreach(print)
    val fangcha = rawData.map { point =>
      val prediction = rfModel.predict(point.features)
      //(point.label, prediction)
      //Math.abs(prediction - point.label)
      (110.13 - point.label)*(110.13 - point.label)
    }

    print("MSE:" + dataAndPreLable.sum()/3040 + "\r\n"
      + "Var:" + fangcha.sum()/3040 + "\r\n"
      + dataAndPreLable.sum()/fangcha.sum())//R^2值为0.949


//    val dataAndPreLable = rawData.map { point =>
//      val prediction = rfModel.predict(point.features)
//      //(point.label, prediction)
//      //Math.abs(prediction - point.label)
//     prediction - point.label + ","
//    }
//    dataAndPreLable.foreach(print)





//    /**
//      * 模拟对新数据进行预测2
//      *
//      */
//    val dataAndPreLable = rawData.map{ line =>
//      //转化为向量并去掉标签(init去掉最后一个元素,即去掉标签)
//      val vecData = Vectors.dense(line.split(",").map(_.toDouble).init)
//      val preLabel = rfModel.predict(vecData)
//      line + "," + preLabel
//    }//.saveAsTextFile("....")
//    dataAndPreLable.take(10)


  }
}
