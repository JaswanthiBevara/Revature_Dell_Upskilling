import org.sparkproject.jetty.server.HttpConfiguration.Customizer

case class Customer(id:Option[Int],name:Option[String],age:Option[Int],email:Option[String])

object DataPipeLineVal {

    def validate(
                cus:Customer
                ):Option[Customer]={
      cus match{
        case Customer(Some(id), Some(name), Some(age), Some(email))
          if id>0 && (age>=18 && age<=100) && name.nonEmpty && email.contains('@') =>
              Some(Customer(Some(id),Some(name),Some(age),Some(email)))
      case _=>
        None
      }
    }

    def main(args:Array[String]):Unit={
      val infos = List(
        Customer(Some(101), Some("John"), Some(25), Some("john@gmail.com")),
        Customer(Some(-102), Some("David"), Some(30), Some("david@yahoo.com")),
        Customer(Some(103), Some(""), Some(22), Some("smith@gmail.com")),
        Customer(Some(104), Some("Peter"), Some(18), Some("peter@gmail.com")),
        Customer(Some(105), Some("Alex"), Some(105), Some("alexgmail.com")),
        Customer(None, Some("Robert"), Some(35), Some("robert@gmail.com")),
        Customer(Some(107), None, None, None),
        Customer(Some(108),Some("Jashu"),Some(15),Some("Jashu@gmail.com"))
      )

      val validcus=infos.flatMap(validate)
      println(s"Valid Customer records: $validcus")
    }
}
