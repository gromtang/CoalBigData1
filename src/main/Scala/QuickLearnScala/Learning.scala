package QuickLearnScala

object Learning {
  //val in = new java.util.Scanner(new java.io.File("D:\\w.txt")) 致命错误

  //val source = Source.fromFile("D:\\w.txt").mkString
  //val words = source.split("\\s+")//得到Array
  //val m = scala.collection.mutable.Map[String,Int]()
  //for(word <- words)
  //  m(word) = m.getOrElse(word,0) + 1

  //var m = java.util.TreeMap[String,Int]


  /*
  val source = Source.fromFile("myfile.txt").mkString

  val tokens = source.split("\\s+")

  val map:Map[String,Int] = new TreeMap[String,Int]

  for(key <- tokens){
      map(key) = map.getOrElse(key,0) + 1
  }

  println(map.mkString(","))
   */
/*
  //C4Q9
  def main(args: Array[String]): Unit = {
    print(q9(Array(1,2,3,4,5,6,7,8,9),5))
  }
  def q9(arr:Array[Int],v:Int):Tuple3[Int,Int,Int]={
    var a,b,c = 0
    for(i <- arr){
      if( i < v) a+= 1
      else if( i == v) b+=1
      else c+=1
      }
    Tuple3(a,b,c)
    }
   */
/*def main(args: Array[String]): Unit = {
  val m = mutable.Map[String, Int](("a", 1))//不能写成("a" , 1)
  m += (("b" , 2))
  //val m1 = Map(("a",1))
  print(m)
  */
/*def main(args: Array[String]): Unit = {


  val props: scala.collection.Map[String, String] = System.getProperties()//Map赋值为方法，每一项是一个Tuple2

  val keys = props.keySet

  val keyLengths = for (key <- keys) yield key.length

  val maxKeyLength = keyLengths.max

  for (key <- keys) {
    print(key)
    print(" " * (maxKeyLength - key.length)) //理解乘号
    print(" | ")
    println(props(key))

  }
}*/
  /*def main(args: Array[String]): Unit = {
    val ct = new ClassTest(1)
    ct.a_=(2)
  }
  */
  /*C5Q4
  def main(args: Array[String]): Unit = {
    val t1 = new C5Q4Time(1,32)
    val t2 = new C5Q4Time(5,40)
    print(t1.before(t2))
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    val ct = new ClassTest
    //ct.a_= "s" 就这一个没生成
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    val name = new C5Q7("Grom Tang")
    print(name.firstName + "\n\r")
    print(name.lastName)
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    val lexus = new C5Q8("lexus","Q5")
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    println(C6.Green)
    println(C6(0))
    //c.main($ -Dscala.time Hello)
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    val c6q6 = new C6Q6
    val f1 = c6q6.fangpian
    println(f1.toString)
    //C6Q6.isRed(f1)// c6q6的Value 跟 C6Q6的Value居然不一样  此处用的是object里的isRed
    c6q6.isRed(f1)//此处就可以是c6q6的fangpian了
  }
  */
  /*
  def main(args: Array[String]): Unit = {
    val a = System.getProperty("user.name")
    println(a)
  }
  */
  /*见asInstanceOf.class，感觉没意义啊
  def main(args: Array[String]): Unit = {
    val c7 = new C7
    var c8:C8 = null
    if(c7.isInstanceOf[C7]) c8 = c7.asInstanceOf[C8]
    //println(c7.a + c7.b)  a还是1 没有b
    println(c8.isInstanceOf[C8])
  }
  */
/*
  //C8Q2 BankAccount perfect!
  def main(args: Array[String]): Unit = {
    val gromtang = new SavingsAccount(10000)
    gromtang.describe
    gromtang.deposit(10)
    gromtang.withdraw(8)
    gromtang.describe
    gromtang.withdraw(1)
    gromtang.withdraw(1)
    gromtang.describe
    gromtang.earnMonthlyInterest
    gromtang.describe
  }
  */
/*
  //C8Q4 Item
  def main(args: Array[String]): Unit = {
    val gromtang = new Bundle
    gromtang.pack(new SimpleItem("PlayStation4",2500))
    gromtang.pack(new SimpleItem("闪之轨迹3",300))
    gromtang.pack(new SimpleItem("闪之轨迹4",300))
    gromtang.description
  }
  */
  def main(args: Array[String]): Unit = {
    //val source = Source.fromFile("D:\\persistent")
    //val arr = source.mkString.toArray
    //val iter = source.buffered
    //while (iter.hasNext){
      //print(iter.next())
    //}
    //source.close()

    //val a = Source.stdin//并未开启输入，而readInt()就可以
/*
    val out = new PrintWriter("D:\\t.txt")
    for(i <- 1 to 10) out.print(i)
    out.close()
    */
  /*
    val dir = "D:\\11game"
    val entries = Files.walk(Paths.get(dir))
    entries.toArray.foreach(p => println(" " + p))
    */
    }
}

