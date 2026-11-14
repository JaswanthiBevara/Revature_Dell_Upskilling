case class Candidate(name:String,score:Option[Int])
object ScoreProcessor {

  def passedCandidates(
                      candidate:Candidate
                      ):Option[String]={
    candidate match{
      case Candidate(name,Some(score)) if score>=60 =>
        Some(name)
      case Candidate(name,Some(score)) =>
        None
      case Candidate(name,None) =>
        None
    }
  }

  def pendingCandidates(
                      candidate:Candidate
                      ):Option[String]={
    candidate match{
      case Candidate(name,None) =>
        Some(name)
      case _ =>
          None
    }
  }

  def failedCandidates(
                    candidate:Candidate
                    ):Option[String]={
    candidate match{
      case Candidate(name,Some(score)) if score<60 =>
        Some(name)
      case _ =>
        None
    }
  }

  def avgScore(
              candidate:Candidate
              ):Option[Int]={
    candidate match{
      case Candidate(name,Some(score)) =>
        Some(score)
      case _=>
        None
    }
  }

  def main(args:Array[String]):Unit={
    val candidates = List(
      Candidate("John", Some(85)),
      Candidate("David", Some(45)),
      Candidate("Smith", None),
      Candidate("Peter", Some(75)),
      Candidate("Alex", None)
    )
    val students_passed = candidates.flatMap(passedCandidates)
    println(s"Passed Candidates: $students_passed")

    val students_pending = candidates.flatMap(pendingCandidates)
    println(s"Pending Candidates: $students_pending")

    val students_failed = candidates.flatMap(failedCandidates)
    println(s"Failed Candidates: $students_failed")

    val scores = candidates.flatMap(avgScore)
    val averageScore:Float = (scores.sum.toFloat/scores.length)
    println(s"Average Score: $averageScore")


  }




}
