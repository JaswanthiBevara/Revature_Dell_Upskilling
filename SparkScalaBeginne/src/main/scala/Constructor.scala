class Student(val name:String,val age:Int) {
  def this(name:String){
    this(name,18)
  }
  def this(){
    this("Guest",18)
  }
}

object Constructor {

  def main (args:Array[String]):Unit= {
    val s1 = new Student("Jaswanthi", 21)
    println("Primary Consturctor: name:"+ s1.name+"," + "age:"+s1.age)
    val s2 = new Student("Jaswanthi")
    println("Parameterized Auxiliary Consturctor: name:"+ s2.name+", age:"+s2.age)
    val s3 = new Student()
    println("Non-Parameterized Auxiliary Consturctor: name: "+s3.name+", age:"+s3.age)
  }

}
