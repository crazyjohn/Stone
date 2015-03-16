package com.stone.scalaer.rational

object RationalTest {
  
  // implicit 
  implicit def intToRational(x: Int) = new Rational(x)
  
  def main(args: Array[String]): Unit = {
    val r1 = new Rational(1, 2)
    val r2 = new Rational(1, 3)
    println(r1 + r2)
    println(r1 + 2)
    println(2 + r1)
  }
}