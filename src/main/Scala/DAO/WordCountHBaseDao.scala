package DAO

import BigDataUtils.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.BasicConfigurator

import scala.collection.mutable.ListBuffer

/**
  * 数据访问实体类
  * HBase的查找单元
  *
  * @param word   HBase表中的rowkey
  * @param count  HBase表中的rowkey对应的value
  */
case class WordCountHBaseDao(word:String,count:Long)

/**
  * 数据访问层
  */
object WordCountHBaseDao {

  val tableName = "wordcount"
  val cf = "info"//列族family
  val qualifier = "word"//列名,hbase里搞错了，应该是count

  /**
    * 保存数据到HBase
    * @param list
    */
  def save(list: ListBuffer[WordCountHBaseDao]): Unit ={

    val table = HBaseUtils.getInstance().getTable(tableName)
    for (ele <- list) {
      table.incrementColumnValue(Bytes.toBytes(ele.word),
        Bytes.toBytes(cf),
        Bytes.toBytes(qualifier),
        ele.count)
    }
  }

  /**
    * 读取
    * @param word
    * @return
    */
  def count(word:String): Long={

    val table = HBaseUtils.getInstance().getTable(tableName)
    val get = new Get(word.getBytes())
    val value = table.get(get).getValue(cf.getBytes(),qualifier.getBytes())
    if(null == value){
      0L
    }else{
      Bytes.toLong(value)
    }
  }

  def main(args: Array[String]): Unit = {

    BasicConfigurator.configure()
    /*
    val list = new ListBuffer[WordCountHBaseDao]
    list.append(WordCountHBaseDao("hadoop",1))
    list.append(WordCountHBaseDao("spark",1))
*/

    //save(list)

    println(count("hadoop"))
  }
}