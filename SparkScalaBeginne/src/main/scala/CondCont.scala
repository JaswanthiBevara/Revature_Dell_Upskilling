object CondCont {
  def main(args:Array[String]):Unit={
    /*val math = 79
    val science = 65
    val English = 87*/
    val math = 45
    val science = 34
    val English = 87
    val total = math+science+English
    println(s"Total Marks: $total")
    val avg = total/3
    println(s"Average Marks: $avg")
    var g = ""
    if(avg>90){
       g="A"
    }
    else if(avg >70){
      g="B"
    }
    else if(avg>50){
      g= "C"
    }
    else{
      g="D"
    }
    println(s"Grade: $g")

    if(math>=35 && science>=35 && English>=35){
      println("Pass")
    }
    else{
      println("Fail")
    }

    for(i<- 1 to 10){
      println(s"i: $i")
    }
    var x= 1
    while(x<=5){
      println(s"X: $x")
      x=x+1
    }

  }

}
