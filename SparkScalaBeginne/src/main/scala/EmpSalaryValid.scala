case class Employe(id:Int,name:String,department:String,salary:Option[Double])

  object EmpSalaryValid {

    def validEmpSal(
                     emp: Employe
                   ): Either[String, Double] = {
      emp match {
        case Employe(id, name, department, Some(salary)) if salary > 0 =>
          Right(salary)
        case Employe(id, name, department, Some(salary)) =>
          Left("Invalid Salary, Salary should be positive")
        case Employe(id, name, department, None) =>
          Left("Salary should not be empty")
      }

    }

    def avgsal(
              emp:Employe
              ):Option[Double]={
    emp match{
      case Employe(id,name,department,Some(salary)) if (department == "IT") =>
            Some(salary)
      case _ =>
        None
    }
    }

    def main(args: Array[String]): Unit = {
      val employees = List(
        Employe(101, "John", "IT", Some(50000)),
        Employe(102, "David", "HR", Some(30000)),
        Employe(103, "Smith", "IT", None),
        Employe(104, "Peter", "Finance", Some(-1000)),
        Employe(105, "Alex", "IT", Some(70000))
      )

      val valsal = employees.map(validEmpSal)
      println(s"Employees Salary Details: $valsal")

      val itemp = employees.filter(emp=>emp.department=="IT")
      println(s"IT Employess: $itemp")

      val sal = employees.flatMap(avgsal)
      println(s"Average salary for IT department: ${sal.sum/sal.length}")


    }

  }

