package Streaming

import DAO.RF_HBaseDAO
import org.apache.commons.lang.StringUtils
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}


object FKS_RF {

  def main(args: Array[String]): Unit = {

//    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
//    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    if (args.length != 4){
      println("UsageL:Flume_Kafka_Streaming needs 4 args")
      System.exit(1)
    }

    val Array(zkQuorum,groupId,topics,numThreads) = args

    val conf = new SparkConf()
      .setAppName("Flume_Kafka_Streaming")
      .setMaster("local[2]")
      .set("spark.executor.memory", "512m")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(5))


    //读取模型
    val rfModel = RandomForestModel.load(sc,"target/tmp/RFModelSimuData1")

    def computePer(line:(String,String)) :Array[RF_HBaseDAO] = {

      val data = line._2.split(" ").map(_.toDouble)
      val dataCurrent = Vectors.dense(data)//实时数据,无label
      val dataStandard = Array[Double](31.3,751.4,220.2,577.3,91.2)//基准值集合
      val resultStandard = 111.4

      val resultCurrent = rfModel.predict(dataCurrent)
      val resultFinal = for(i <- 0 to dataStandard.length - 1) yield {

        data(i) = dataStandard(i)
        val resultSwitched = rfModel.predict(Vectors.dense(data))
        val iterPer :Double =
          (resultCurrent - resultSwitched) / (resultCurrent - resultStandard)
        RF_HBaseDAO(i, iterPer)
      }
      resultFinal.toArray
    }

    //将多个topic分割，map成(topic1,numThreads)。
    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap
    val messages = KafkaUtils.createStream(ssc, zkQuorum, groupId, topicMap).persist()
    //计算，并写入hbase
    val resultFinal = messages.foreachRDD(_.foreach(line => if(!StringUtils.isEmpty(line._2)) {
      val data = computePer(line)
      data.foreach(print)
      RF_HBaseDAO.save(data)
    }))

    ssc.start()
    ssc.awaitTermination()

//旧版，转为labeledpoint再计算
//    //将messages中的内容先判断是否为空,非空则转化为labeledpoint并输入模型预测，打印出结果。
//    //predict方法接收的是Vector，没必要是labeledpoint,正好实际上实时数据不会有point.label，只会有features
//
//    val pattern = "\\d\\:".r
//    val rddlabel = messages.foreachRDD(_.foreach{
//      arr => if(arr._2 != ""){
//        val point = create_Label_Point(arr._2)
//        val prediction = rfModel.predict(point.features)
//        println((point.label, prediction))
//        point
//      }
//        arr
//    })

    //wordcount版本

//      .flatMap(_.split(" "))
//      .map((_, 1))
//      .reduceByKey(_ + _)
//      .foreachRDD(rdd => {
//        rdd.foreachPartition(partitionRecords => {
//
//          val list =  new ListBuffer[WordCountHBaseDao]
//          partitionRecords.foreach(pair => {
//            list.append(WordCountHBaseDao(pair._1,pair._2))
//          })
//          WordCountHBaseDao.save(list)
//
//        })
//      })


  }

//  不需要转换成LabeledPoint了
//  def create_Label_Point(line:String):LabeledPoint = {
//    //字符串去空格，以逗号分隔转为数组
//    val lineArr = line.trim().split(" ")
//    val lineDoubleArr = lineArr.map(x => x.toDouble)
//    //定长数组转可变数组
//    val lineArrBuff = lineDoubleArr.toBuffer
//    //移除label元素（将linedoublearr的第一个元素作为标签）
//    lineArrBuff.remove(0)
//    //将剩下的元素转为向量
//    val vectorArr = lineArrBuff.toArray
//    val vector = Vectors.dense(vectorArr)
//    //返回标签向量
//    LabeledPoint(lineDoubleArr(0), vector)
//  }

}
