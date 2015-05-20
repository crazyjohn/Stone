package com.stone.tools.test.traits

object QueueApp {
  def main(args: Array[String]): Unit = {
    val queue = new BasicQueue
    queue.put(10)
    queue.put(20)
    println(queue.get())
    println(queue.get())
    // doubling
    val doubleQueue = new MyQueue
    doubleQueue.put(10)
    println(doubleQueue.get())
    // just new
    val noNameQueue = new BasicQueue with Doubling
    noNameQueue.put(10)
    println(noNameQueue.get())
    
    // mix trait
    val mixQueue = new BasicQueue with Increamenting with Filtering
    mixQueue.put(-1)
    mixQueue.put(0)
    mixQueue.put(1)
    println(mixQueue.get())
    println(mixQueue.get())
    
  }
}