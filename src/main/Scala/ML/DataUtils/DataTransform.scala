package ML.DataUtils

import org.apache.spark.{SparkConf, SparkContext}

object DataTransform {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("DataTransform").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("C:\\Users\\gromtang\\Desktop\\调研\\csv\\ss.csv")

    //val libData = data.map(_.split(",")).filter(rdf => rdf(1) != "0")//会变成内存地址???



//    val libData = data.map(_.split(",").toArray[String]).map(arr =>
//      {
//        val coal = (1000 * arr(0).toDouble).toString
//        var str = if (coal.toDouble >= 100) {if (coal.length <= 7) coal else coal.substring(0,7)}
//                  else {if (coal.length <= 6) coal else coal.substring(0,6)}
//        for (i <- 1 to arr.length - 1) {str += " "+ i + ":" + arr(i)}
//        str
//        })

//        val libData = data.map(_.split(",").toArray[String]).map(arr =>
//        {
//          val clas = if(arr(0).toDouble >= 69) ",0"
//            else if (arr(0).toDouble < 69) ",1"
//          var str =  arr(0).toString + clas
//          for (i <- 1 to arr.length - 1) {str += ( "," + arr(i))}
//          str
//        })

        val libData = data.map(_.split(",").toArray[String]).map(arr =>
        {
          var str =  arr(0)
          for (i <- 1 to arr.length - 1) {str +=  " " + i + ":" + arr(i)}
          str
        })

    libData.saveAsTextFile("C:\\Users\\gromtang\\Desktop\\调研\\csv\\sslib000")
    //    //稳定性选择用的转换
    //    val libData = data.map(_.split(",").toArray[String]).map(arr =>
    //    {
    //      val clas = if(arr(0).toDouble >= 0.115) ",0"
    //        else if (arr(0).toDouble >= 0.100 && arr(0).toDouble < 0.115) ",1"
    //        else if (arr(0).toDouble < 0.100) ",2"
    //      var str = (1000 * arr(0).toDouble).toString + clas
    //      for (i <- 1 to arr.length - 1) {str += ( "," + arr(i))}
    //      str
    //    })

//    val out = new PrintWriter("C:\\Users\\gromtang\\Desktop\\调研\\csv\\libData.csv")
//    for(i <- libData) {
//        out.println(i)
//    }
//    out.close()
  }
}
