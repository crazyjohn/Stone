package com.stone.tools.test

object CollectionFunctions {

  def containsNeg(nums: List[Int]): Boolean = {
    var exists = false
    for (num <- nums) {
      if (num < 0) {
        exists = true
      }
    }
    exists
  }

  def containsNeg2(nums: List[Int]) = nums.exists { _ < 0 }

  def main(ars: Array[String]): Unit = {
    val nums = List(10, 12, 100, 0, -5, 111)
    println(CollectionFunctions.containsNeg(nums))
    // 2 way
    println(CollectionFunctions.containsNeg2(nums))
  }
}