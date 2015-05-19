package com.stone.tools.test

object FindLongLines {
  def main(args: Array[String]) = {
    val width = 45
    LongLines.processFile(System.getProperty("user.dir") + "\\resources\\LongLines.scala", width)
  }
}