package QuickLearnScala

class C5Q4Time(val hrs:Int ,val min: Int) {
  private[this] var _hours: Int = 0

  private def hours: Int = _hours

  private[this] var _minutes: Int = 0

  private def minutes: Int = _minutes

  if(hrs >= 0 && hrs < 24 && min >= 0 && min <60) {
    _hours = hrs
    _minutes = min
  }
  else {
    throw new Exception{
      print("Time Error")}
  }
/*
  def before(other:C5Q4Time): Boolean ={
    if(_hours < other.hours || (_hours == other.hours && _minutes < other.minutes)) true
    else false
  }
  */
  def before(other:C5Q4Time): Boolean = {
    if (_hours * 60 + _minutes < other.hours * 60 + other.minutes) true
    else false
  }
}
