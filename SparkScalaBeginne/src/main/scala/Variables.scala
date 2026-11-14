object Variables {
  def main(args:Array[String]):Unit={
     val name = "Jaswanthi"
    val course = "Scala"
    var age =21
    var fee = 5000
    println(s"Name: $name and age : $age")
    println(s"Course: $course and Fee: $fee")
    // name = "Bevara Jaswanthi"  It throws an error because we  val type variable is immutable we can't reassign value to val variable
    
  }
}