package QuickLearnScala

class C6 extends App {
  val c6 = 1
}

object C6 extends App{

  if(args.length > 0) println(f"Hello ${args(0)}")
  else println("Hello,World")

} //不理解！！！！！！！！！！！！！

/*
object C6 extends Enumeration {

  val Red, Yellow, Green = Value
}
 */
/*
class C6(a:Int,b:Int){
  
}
object C6 {
  def apply(a: Int, b: Int): C6 = new C6(a, b)
}
*/
/*完全不明白命令行参数！！！！！！！！
object Reverse extends App{

  args.reverse.foreach(arg => print(arg  + " "))

}*/


