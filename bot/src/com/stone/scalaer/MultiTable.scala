package com.stone.scalaer

object MultiTable {
  
  def makeRowSeq(row: Int) = {
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString()
      val padding = " " * (4 - prod.length())
      padding + prod
    }
  }
  
  def makeRow(row: Int) = this.makeRowSeq(row).mkString
  
  def multiTable() = {
    val talbeSeq = 
      for (row <- 1 to 10)
      yield makeRow(row)
    talbeSeq.mkString("\n")
  }
  
  def main(args: Array[String]):Unit = {
    println(multiTable)
  }
}