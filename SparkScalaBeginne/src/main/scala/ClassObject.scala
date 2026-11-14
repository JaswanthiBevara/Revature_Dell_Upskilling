class Book(val title:String,val author:String,val price:Float) {
  def dispalyDetails():Unit={
    println(s"Book Name: $title")
    println(s"Author: $author")
    println(s"Price: $price")
  }
}
object ClassObject {
  def main(args:Array[String]):Unit={
    val b1 = new Book("Together","Aakash",200)
    val b2 = new Book("113","Jashu",180)
    b1.dispalyDetails()
    b2.dispalyDetails()
  }

}
