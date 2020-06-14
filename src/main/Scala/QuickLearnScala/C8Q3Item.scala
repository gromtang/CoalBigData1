package QuickLearnScala

import scala.collection.mutable

abstract class C8Q3Item {
  def price : Double
  def description : String
}

class SimpleItem(override val description: String,
                 override val price: Double) extends C8Q3Item{

}

class Bundle {

  private val arr = mutable.Map[String,Double]() //不能有new！！！！！！！！！！！

  def pack(item: SimpleItem): Unit ={
    arr += (item.description -> item.price)
  }

  def description: Unit ={
    var des:String = ""
    var pri:Double = 0
    for((k,v) <- arr) {
      des += k + ","
      pri += v
    }
    println("购物车内的商品有:" + des + "总价为:" + pri)
  }

}
