package QuickLearnScala

import java.nio.file.{Files, Paths}

import scala.io.Source

class C9 {
}
/*
object C9 extends App{

  /*
  val gromtang = new Person3
  val out = new ObjectOutputStream(new FileOutputStream("D:\\test.obj"))
  out.writeObject(gromtang)
  out.close()
  val in = new ObjectInputStream(new FileInputStream("D:\\test.obj"))
  val savedGromtang = in.readObject().asInstanceOf[Person3]
*/
  /*
  val str = "1 23 4"
  val pattern = "\\s+[0-9]+".r
  val str2 = pattern.replaceSomeIn(str, m => if(m.matched.trim.toInt % 2 == 1) Some("XX") else None)
  print(str2)
  */
  /*
  val gpattern = "([0-9]+) ([a-z]+)".r("num","item")
  for(m <- gpattern.findAllMatchIn("12 ss 135 ssawd 14 dsw"))
    print(m.group("num") + " ")//同group(1)
  */
  /*
  val numitemPattern = "([0-9]+) ([a-z]+)".r
  val numitemPattern(num,item) = "11 it"
  print(num + item)
  */
  val numitemPattern = "([0-9]+) ([a-z]+)".r
  for(numitemPattern(num,item) <- numitemPattern.findAllMatchIn("11 it 12 item"))
    print(num + " ")

}
*/

object C9Q1 extends App {
  val source = Source.fromFile("D:\\words.txt")
  val line = source.getLines()
  line.toArray.reverse.foreach(l => println(l))
  source.close()
}

object C9Q3 extends App{

  val line = Source.fromFile("D:\\C9Q3.txt").mkString.split("\\s+").
    filter(i => i.length > 12).foreach(i => print(i + " "))
}

object C9Q4 extends App{

  val source = Source.fromFile("D:\\C9Q4.txt")
  val line = source.mkString
  val pattern = "[0-9]+\\.[0-9]+".r
  val arr = pattern.findAllIn(line).map(_.toDouble).toArray[Double]
  //加map是第二种，不加map就加上下面注释掉的
  //var sum : Double = 0
  //val arrD = (for (i <- arr) yield i.toDouble).toArray[Double]
  //for (i <- arr) sum += i
  //val avg = sum/arr.length
  println("所有值之和为:" + arr.sum)
  println("平均值为:" + arr.sum/arr.length)
  println("最大值:" + arr.max)
}

object C9Q8 extends App{
  val url = "https://blog.csdn.net/norhtstorm/article/details/62898754"
  val str = Source.fromURL(url).mkString
  val pattern = "<img\\s+src.*>".r
  for (imgsrc <- pattern.findAllIn(str)) println(imgsrc)

  //val url = "https://blog.csdn.net/norhtstorm/article/details/62898754"
  //val in = Source.fromURL(url, "UTF-8").mkString("")
  //val gre = "(.*?)(<img\\s+?src.+?>)(.*?)".r
  //for (gre(a, b, c) <- gre.findAllIn(in)) { println(b) }
}

object C9Q9 extends App{
  val name = Files.walk(Paths.get("D:\\Java\\code"))
  //for(i <- name.toArray){
  //  if (i.toString.endsWith(".class")) println(i)
  //}
  val p = ".+\\.class".r
  var sum = 0
  for(i <- name.toArray)
    if(p.findFirstIn(i.toString).toString != "None")
      sum += 1
  println(sum)

}