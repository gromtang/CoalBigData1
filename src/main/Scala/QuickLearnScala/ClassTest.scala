package QuickLearnScala

import scala.beans.BeanProperty

/*
class QuickLearnScala.ClassTest {
  private[this] var _a: Int = 0

  private def a: Int = _a

  private def a_=(value: Int): Unit = {
    _a = value
  }
}
*/
/*class ClassTest(private var _a :Int) {
  //private[this] var _a: Int = 0

  def a: Int = _a

  def a_=(value: Int): Unit = {
    _a = value
  }

  print(_a)

}
*/
class ClassTest{
  @BeanProperty var a:String = _
}
