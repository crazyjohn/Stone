package com.stone.scalaer
import scala.collection.mutable.Map
object ForTest extends App {
  val greetings = new Array[String](3)
  greetings(0) = "hello"
  greetings(1) = "scala"
  greetings(2) = ", john"
  greetings.foreach { (greeting:String) => {println(greeting)} }
  for (i <- 0 to 2) {
    println(greetings(i))
  }
  
  val arrays = Array("one", "two", "three")
  
  val list = List[Int]()
  
  val set = Set[Int]()
  
  val map = Map[Int, String]()
  map += (1 -> "test")
  println(map(1))
}