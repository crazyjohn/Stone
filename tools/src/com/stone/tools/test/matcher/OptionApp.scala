package com.stone.tools.test.matcher

object OptionApp {

  def show(x: Option[String]) = x match {
    case Some(s) => s
    case None    => "?"
  }
  
  val withDefault: Option[Int] => Int = {
    case Some(x) => x
    case None => 0
  }

  def main(args: Array[String]): Unit = {
    val capitals = Map("China" -> "beijing", "USA" -> "NewY")
    println(show(capitals get "China"))
    println(show(capitals get "USA"))
    println(show(capitals get "NO"))
    // foreach
    for ((country, city) <- capitals) {
      println("The capital of " + country + " is " + city)
    }
    // do not match
    val results = List(Some("apple"), None, Some("Orange"))
    for (Some(fruit) <- results) {
      println(fruit)
    }
  }
}