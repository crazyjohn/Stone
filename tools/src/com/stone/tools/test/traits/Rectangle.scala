package com.stone.tools.test.traits

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular with Ordered[Rectangle]{
  def compare(that: Rectangle) = {
    this.width - that.width
  }
}