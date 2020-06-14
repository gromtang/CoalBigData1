package QuickLearnScala

import scala.util.matching.Regex

class C5Q7(val str:String) {
  private val pattern = new Regex("[A-Z][a-z]+\\s[A-Z][a-z]+")
  private val str2 = pattern.findAllIn(str).mkString
  private val arr = str2.split(" ")
  private val _firstName = arr(0)
  private val _lastName = arr(1)

  def firstName:String = _firstName
  def lastName:String = _lastName

}
