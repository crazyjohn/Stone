package com.stone.tools.test.traits

import scala.collection.mutable.ArrayBuffer

class BasicQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  
  def get() = buf.remove(0)
  
  def put(x: Int) = {
    buf += x
  }
}