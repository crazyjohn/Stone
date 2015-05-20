package com.stone.tools.test.traits

trait Filtering extends IntQueue {
  abstract override def put(x: Int) {
    if (x >= 0) {
      super.put(x)
    }
  }
}