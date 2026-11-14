object Operators {
  def main(args:Array[String]):Unit={
    val a = 12
    val b = 6
    println(s"Addition: ${a+b}")
    println(s"Subtraction: ${a-b}")
    println(s"Multiplication: ${a*b}")
    println(s"Division: ${a/b}")
    println(s"Remainder: ${a%b}")

    println(s"a greater than b: ${a>b}")
    println(s"a less than b: ${a<b}")
    println(s"a equals to b: ${a==b}")

    if(a>0 && b>0){
      println("Both number s are positive")
    }
    if(a%2==0 || b%2==0){
      println("Atleast one number is Even")
    }
  }
}