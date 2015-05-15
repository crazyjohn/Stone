package com.stone.tools.test

import com.sun.org.apache.xpath.internal.axes.NodeSequence

object XmlApp extends App {
  val therm = new CCTherm {
    val desc = "hot dog #5"
    val yearMade = 1952
    val bookPrice = 2199
  }
  // node 
  val node = therm.toXML
  println(node)
  
}