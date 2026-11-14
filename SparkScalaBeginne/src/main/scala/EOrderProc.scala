case class Order(orderId:Int,customerName:String,amount:Option[Double],status:String)

object EOrderProc{

  def valiAmount(
                ord:Order
                ):Option[Int]= {
    ord match {
      case Order(orderId, customerName, Some(amount), status) if amount >0=>
        Some(orderId)
      case _ =>
        None
    }
  }

  def oreCom(
            ord:Order
            ):Option[Order]={
    ord match{
      case Order(orderId, customerName, Some(amount), status) if status=="COMPLETED" =>
        Some(Order(orderId, customerName, Some(amount), status))
      case Order(orderId, customerName, None, status) if status=="COMPLETED" =>
        Some(Order(orderId, customerName, None, status))
      case Order(orderId, customerName, Some(amount), status) =>
        None

    }
  }

  def totalAmount(
                 ord:Order
                 ):Option[Double]={
          ord match {
            case Order(orderId, customerName, Some(amount), status) if amount>0 && status=="COMPLETED" =>
              Some(amount)
            case _ =>
              None
          }
  }

  def penOrd(
              ord:Order
            ):Option[Order]={
    ord match{
      case Order(orderId, customerName, Some(amount), status) if status=="PENDING" =>
        Some(Order(orderId, customerName, Some(amount), status))
      case Order(orderId, customerName, None, status) if status=="PENDING" =>
        Some(Order(orderId, customerName, None, status))
      case _ =>
        None

    }
  }

  def valid(
           ord:Order
           ):Option[(Int,Double)]={
    ord match{
      case Order(orderId, customerName, Some(amount), status)  if amount>0 =>
        Some((orderId,amount))
      case _=>
        None
    }
  }

  def main(args:Array[String]):Unit= {
    val orders = List(
      Order(1, "John", Some(2500), "COMPLETED"),
      Order(2, "David", Some(1500), "PENDING"),
      Order(3, "Smith", None, "COMPLETED"),
      Order(4, "Peter", Some(-500), "FAILED"),
      Order(5, "Alex", Some(4000), "COMPLETED")
    )
    val valamount = orders.flatMap(valiAmount)
    println(s"Valid Amount orders: $valamount")

    val comord = orders.map(oreCom)
    println(s"Comleted orders: $comord")

    val total = orders.flatMap(totalAmount).sum
    println(s"Total Amount of valid and Completed Orders: $total")

    val pen = orders.flatMap(penOrd)
    println(s"Pending Orders: $pen")

    val validOrd=orders.flatMap(valid)
    println(s"Valid Order's Id and Amount: $validOrd")

  }


}
