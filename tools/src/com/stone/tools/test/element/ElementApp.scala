
package com.stone.tools.test.element

/**
 * @author crazyjohn
 *
 */
object ElementApp {
  
  def main(args: Array[String]): Unit = {
    val element = new ArrayElement(Array("hello", "element"))
    println(element.width)
    println(element.height)
  }
  
}