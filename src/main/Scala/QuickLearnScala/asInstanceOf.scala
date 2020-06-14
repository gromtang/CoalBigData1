package QuickLearnScala

class Person3 extends Serializable {}
class Student3 extends Person3
object Student3{
  def main (args: Array[String] ) {
    val p: Person3 = new Student3
    var s: Student3 = null
    //如果对象是 null，则 isInstanceOf 一定返回 false
    println (s.isInstanceOf[Student3])
    // 判断 p 是否为 Student3 对象的实例
    if (p.isInstanceOf[Student3] ) {
      //把 p 转换成 Student3 对象的实例
      s = p.asInstanceOf[Student3]
    }
    println (s.isInstanceOf[Student3] )
  }
}