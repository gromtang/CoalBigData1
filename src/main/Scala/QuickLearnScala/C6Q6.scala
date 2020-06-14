package QuickLearnScala

class C6Q6 extends Enumeration {
  val meihua = Value("♣")
  val fangpian = Value("♦")
  val hongtao = Value
  val heitao = Value

  def isRed(pok:Value):Boolean={
    if(pok == "♦" || pok == "♥") true
    else false
  }
  //class里的方法可以被对象调用，object里的不行！！
}
object C6Q6 extends Enumeration{

  def isRed(pok:Value):Boolean={
    if(pok == "♦" || pok == "♥") true
    else false
  }

}
