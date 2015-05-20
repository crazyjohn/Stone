package com.stone.tools.test.traits

object ShapeApp {
  def main(args: Array[String]): Unit = {
    val rect = new Rectangle(new Point(1, 1), new Point(10, 10))
    println(rect.left)
    println(rect.width)
  }
}