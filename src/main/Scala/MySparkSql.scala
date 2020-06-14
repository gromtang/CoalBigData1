import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object MySparkSql {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("MySparkSql").setMaster("local")
    val sc = new SparkContext(conf)

//    val ss = new SparkSession(sc)//SQLContext
//
//    ss.sqlContext.sql("")
//    ss.stop()
//    sc.stop()

    val ss = SparkSession.builder()
      .appName("SQLtest").master("local").enableHiveSupport().getOrCreate()
    //后两个必备,否则无sql方法
    val query = ss.sql("SELECT SUM(t.1),SUM(t.2)…… FROM t_coal t WHERE t.date LIKE '%21300202'")
    query.rdd
    query.show()
    ss.close()

   //val hc = new HiveContext(sc)
  }
}
