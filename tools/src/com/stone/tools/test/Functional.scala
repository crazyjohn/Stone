package com.stone.tools.test

object Functional {
  val increase = (x: Int) => {
    println("we")
    println("are")
    println("here")
    x + 1
  }

  def main(args: Array[String]) {
    println(increase(1))
    // foreach
    val someNumbers = List(-11, -10, -5, 0, 5, 10)
    someNumbers.foreach { (x: Int) => println(x) }
    // filter
    println("filter")
    println(someNumbers.filter { (x: Int) => x > 0 })
  }
}