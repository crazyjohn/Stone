package com.stone.tools.test.element
/**
 * The elements;
 *
 * @author crazyjohn;
 */
abstract class Element {

  def contents: Array[String]

  /**
   * Get the height;
   */
  def height: Int = contents.length

  /**
   * Get the width;
   */
  def width: Int = {
    if (height == 0) {
      0
    } else {
      contents(0).length()
    }
  }

}