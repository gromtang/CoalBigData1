package QuickLearnScala

class C5Q8(val brand:String,val code:String,val year:Int,var num:String) {
  print(brand + code + year + num)
  def this(brand:String, code:String){
    this( brand, code, year = -1, num = "")
  }
}
