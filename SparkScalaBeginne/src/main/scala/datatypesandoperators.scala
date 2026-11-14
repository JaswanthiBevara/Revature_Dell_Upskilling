object datatypesandoperators {

  def main(args: Array[String]): Unit = {

    // 1. DATA TYPES

    val studentId: Int = 101
    val studentName: String = "Rajesh"
    val age: Short = 25
    val mobileNumber: Long = 9876543210L
    val marks: Double = 85.75
    val percentage: Float = 85.5f
    val grade: Char = 'A'
    val isPassed: Boolean = true
    val smallNumber: Byte = 100

    println("========== STUDENT DETAILS ==========")

    println("Student ID: " + studentId)
    println("Student Name: " + studentName)
    println("Age: " + age)
    println("Mobile Number: " + mobileNumber)
    println("Marks: " + marks)
    println("Percentage: " + percentage)
    println("Grade: " + grade)
    println("Passed: " + isPassed)
    println("Byte Value: " + smallNumber)


    // 2. ARITHMETIC OPERATORS

    val subject1 = 80
    val subject2 = 90
    val subject3 = 70

    val total = subject1 + subject2 + subject3
    val average = total / 3.0

    println("\n========== ARITHMETIC OPERATORS ==========")

    println("Addition: " + (subject1 + subject2))
    println("Subtraction: " + (subject1 - subject2))
    println("Multiplication: " + (subject1 * subject2))
    println("Division: " + (subject1 / subject2))
    println("Modulus: " + (subject1 % 3))

    println("Total Marks: " + total)
    println("Average Marks: " + average)


    // 3. RELATIONAL OPERATORS

    println("\n========== RELATIONAL OPERATORS ==========")

    println("Marks Equal to 80: " + (subject1 == 80))
    println("Marks Not Equal to 90: " + (subject1 != 90))
    println("Marks Greater than 50: " + (subject1 > 50))
    println("Marks Less than 100: " + (subject1 < 100))
    println("Marks Greater or Equal to 80: " + (subject1 >= 80))
    println("Marks Less or Equal to 80: " + (subject1 <= 80))


    // 4. LOGICAL OPERATORS

    val attendance = 85

    val eligible =
      subject1 >= 40 && attendance >= 75

    val scholarship =
      subject1 >= 90 || attendance >= 90

    println("\n========== LOGICAL OPERATORS ==========")

    println("Eligible: " + eligible)
    println("Scholarship Eligible: " + scholarship)
    println("Not Passed: " + !isPassed)


    // 5. ASSIGNMENT OPERATORS

    var bonusMarks = 5

    println("\n========== ASSIGNMENT OPERATORS ==========")

    bonusMarks += 5
    println("After += : " + bonusMarks)

    bonusMarks -= 2
    println("After -= : " + bonusMarks)

    bonusMarks *= 2
    println("After *= : " + bonusMarks)

    bonusMarks /= 2
    println("After /= : " + bonusMarks)


    // 6. UNARY OPERATORS

    val number = 10

    println("\n========== UNARY OPERATORS ==========")

    println("Positive: " + (+number))
    println("Negative: " + (-number))
    println("Logical NOT: " + (!isPassed))


    // 7. FINAL STUDENT RESULT

    println("\n========== FINAL RESULT ==========")

    if (eligible) {
      println(studentName + " is eligible to pass.")
    } else {
      println(studentName + " is not eligible to pass.")
    }

    println("Student Total: " + total)
    println("Student Average: " + average)
    println("Student Grade: " + grade)

  }
}
