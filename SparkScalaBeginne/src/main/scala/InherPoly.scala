class Employee(val name:String,val salary:Float) {
  def displayDetails():Unit={
    println(s"Name: $name, Salary: $salary")
  }
  def workDescription():Unit={
    println("Being responsible Employee")
  }
}

class Manager(name :String, salary:Float,val bonus:Float) extends Employee(name,salary) {
  def sal():Float={
    salary+bonus
  }
  override def workDescription():Unit={
    println("Managing the Team and Taking the Team Responsibility")
  }
}

class Intern(name:String, salary:Float,val stipend:Float) extends Employee(name,salary) {
  def sal():Float={
    salary+stipend
  }
  override def workDescription():Unit={
    println("Learnning the industrial Work and gaining Pratical Exposure")
  }
}

object InherPoly {
  def main(args:Array[String]):Unit={
    val m=new Manager("Jaswanthi",50000,10000)
    val n=new Intern("Kathik",20,15000)
   println("Manager sal:"+m.sal())
    println("Intern Sal:"+n.sal())
    val lst=List(m,n)
    for(i<-lst){
      i.displayDetails()
      i.workDescription()
    }
  }
}
