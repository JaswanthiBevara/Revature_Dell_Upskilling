case class Subject1(name: String, marks: Option[Int])
case class Student1(id: Int, name: String, Subjects: List[Subject1])

object StdGradeMang {

  def isIncomplete(student: Student1): Boolean = {
    student match {
      case Student1(id, name, subjects) =>
        subjects.exists {
          case Subject1(subjectName, None) => true
          case Subject1(subjectName, Some(marks)) => false
        }
    }
  }

  def totalMarks(student: Student1): Either[String, Int] = {
    student match {
      case Student1(id, name, subjects) if isIncomplete(student) =>
        Left("Assessments are incomplete")

      case Student1(id, name, subjects) =>
        val total = subjects.flatMap {
          case Subject1(subjectName, Some(marks)) => List(marks)
          case Subject1(subjectName, None) => List.empty[Int]
        }.sum

        Right(total)
    }
  }

  def isPassed(student: Student1): Boolean = {
    student match {
      case Student1(id, name, subjects) =>
        !isIncomplete(student) &&
          subjects.forall {
            case Subject1(subjectName, Some(marks)) => marks >= 40
            case Subject1(subjectName, None) => false
          }
    }
  }

  def hasDistinction(student: Student1): Boolean = {
    totalMarks(student) match {
      case Right(total) =>
        total.toDouble / student.Subjects.size >= 75

      case Left(error) =>
        false
    }
  }

  def main(args: Array[String]): Unit = {

    val students = List(
      Student1(101, "Jashu", List(
        Subject1("Scala", Some(90)),
        Subject1("SQL", Some(85)),
        Subject1("Python", Some(95))
      )),
      Student1(102, "David", List(
        Subject1("Scala", Some(60)),
        Subject1("SQL", Some(70)),
        Subject1("Python", Some(65))
      )),
      Student1(103, "Smith", List(
        Subject1("Scala", Some(80)),
        Subject1("SQL", None),
        Subject1("Python", Some(90))
      )),
      Student1(105, "Alex", List(
        Subject1("Scala", None),
        Subject1("SQL", Some(80)),
        Subject1("Python", None)
      )),
      Student1(106, "Robert", List(
        Subject1("Scala", Some(95)),
        Subject1("SQL", Some(90)),
        Subject1("Python", Some(85))
      ))
    )

    println("STUDENT-WISE TOTAL MARKS")
    students.foreach { student =>
      println(student.name + " : " + totalMarks(student))
    }

    println("\nSTUDENTS WHO PASSED")
    students.filter(isPassed).foreach(println)

    println("\nINCOMPLETE ASSESSMENTS")
    students.filter(isIncomplete).foreach(println)

    println("\nSTUDENTS WITH DISTINCTION")
    students.filter(hasDistinction).foreach(println)
  }
}