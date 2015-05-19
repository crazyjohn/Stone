package com.stone.tools.test

import scala.io.Source

object LongLines {

  def processFile(fileName: String, width: Int) {
    val source = Source.fromFile(fileName)
    for (line <- source.getLines()) {
      processLine(fileName, width, line)
    }
  }

  private def processLine(fileName: String, width: Int, line: String) {
    if (line.length() > width) {
      println(fileName + ": " + line.trim())
    }
  }

}