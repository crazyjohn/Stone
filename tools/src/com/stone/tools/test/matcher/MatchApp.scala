package com.stone.tools.test.matcher

object MatchApp {

  def describe(x: Any) = x match {
    case 5      => "five"
    case true   => "truth"
    case "helo" => "hi!"
    case Nil    => "the empty list"
    case _      => "something else"
  }

  def constructorMatch(x: Any) = x match {
    case List(0, _, _) => "found it"
    case _             => "default"
  }

  def tupleMatch(x: Any) = x match {
    case (a, b, c) => "matched " + a + b + c
    case _         =>
  }

  def typeMatch(x: Any) = x match {
    case s: String    => s.length()
    case m: Map[_, _] => m.size
    case i: Int       => i
    case _            => 1
  }

  def main(args: Array[String]): Unit = {
    // const match
    //    println(describe(5))
    //    println(describe(true))
    //    println(describe("hello"))
    //    println(describe(Nil))
    //    println(describe(List(1, 2, 3)))
    // constructor match
    //    println(constructorMatch(List(0, 1)))
    //    println(constructorMatch(List(0, 1, 2)))
    // tuple match
    //    println(tupleMatch(("a", 3, "-tuple")))
    // type match
    println(typeMatch("hello, scala"))
    println(Map(1 -> 'a', 2 -> 'b'))
    println(typeMatch(5))
  }
}