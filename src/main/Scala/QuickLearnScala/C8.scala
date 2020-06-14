package QuickLearnScala

class C8 extends C7 {
  //val b = 2
  //override val a = 1
  def range = 10
}
object C8{
  def main(args: Array[String]): Unit = {
    val alien = new Person3(){
      def greeting = "aaaa"
    }
  }
}

class C8Z extends C8{
  override val range: Int = super.range
}