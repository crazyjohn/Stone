package com.stone.tools.test.traits

trait Increamenting extends IntQueue {
  abstract override def put(x: Int) {
    super.put(x + 1)
  }
}