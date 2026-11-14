object IntExtention {
  implicit class RichInt(num:Int) {
    def square():Int={
      num*num
    }
  }
}

object Implicit {
  def main(args:Array[String]):Unit={
    import IntExtention._
    //val n = 4
    val n=7
    val sqr = n.square()
    println(s"Square: $sqr")
  }

}
