package DAO

import BigDataUtils.HBaseUtils
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes

/**
  * 数据访问实体类
  * HBase的查找单元
  *
  * @param index   HBase表中的rowkey       //rowkey并非索引，HBase中没有真正的索引，数据是顺序排列的
  * @param per  HBase表中的rowkey对应的value
  */
case class RF_HBaseDAO(index:Int, per:Double)

/**
  * 数据访问层
  */
object RF_HBaseDAO {

  val tableName = "RF_test"
  val cf = "data"//列族Column family,还可以追加进去其他列，如能耗偏差值
  val qualifier = "per" //列名

  /**
    * 保存数据到HBase
    *
    * @param list
    */
  def save(list: Array[RF_HBaseDAO]): Unit = {

    val table = HBaseUtils.getInstance().getTable(tableName)
    for (ele <- list) {
      val put = new Put(Bytes.toBytes(ele.index.toString))
      put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(qualifier),Bytes.toBytes(ele.per.toString.substring(0,4)))
      table.put(put)
    }
  }
}