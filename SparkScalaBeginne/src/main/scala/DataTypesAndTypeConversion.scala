object DataTypesAndTypeConversion {
  def main(args:Array[String]):Unit={
    val prod_name:String = "Mobile"
    var quantity:Int = 2
    val price: Double = 50000
    var availability:Boolean = true
    val grade:Char = 'T'
    val total_price = quantity*price
    println(s"Total_Price: $total_price")
    val DI = price.toInt
    println(s"Original Price: $price, Converted Price: $DI")
    val ID = quantity.toDouble
    println(s"Original quantity: $quantity, Converted Price: $ID")
  }
}