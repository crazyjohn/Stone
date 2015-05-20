package com.stone.tools.test.traits

trait Rectangular {
  // just declare
  def topLeft: Point
  def bottomRight: Point
  
  def left = topLeft.x
  def right = bottomRight.x
  
  def width = right - left
}