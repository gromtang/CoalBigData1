package QuickLearnScala

class BankAccount(initialbalance:Double){

  private var _balance: Double = initialbalance

  def balance: Double = _balance

  def balance_=(value: Double): Unit = {
    _balance = value
  }

  def deposit(amount:Double) = {_balance += amount; _balance}
  def withdraw(amount:Double) = {_balance -= amount; _balance}

}

class SavingsAccount(initialbalance:Double) extends BankAccount(initialbalance:Double){
  var n = 3
  override def deposit(amount: Double): Double = {
    if(n > 0) {n -=1; super.deposit(amount)}
    else {super.deposit(amount - 1)}
  }
  override def withdraw(amount: Double): Double = {
    if(n > 0) {n -=1; super.withdraw(amount)}
    else {super.withdraw(amount + 1)}
  }

  def earnMonthlyInterest = {
    n = 3
    super.balance_=(super.balance * 1.003)
  }

  def describe = {
    println("当前余额:" + super.balance + ",剩余免费存取次数:" + n)
  }
}
