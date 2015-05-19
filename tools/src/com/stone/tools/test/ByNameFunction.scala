package com.stone.tools.test

object ByNameFunction {

  var assertEnabled = true
  /**
   * First version;
   */
  def myAssert(predicate: () => Boolean) {
    if (assertEnabled && !predicate()) {
      throw new AssertionError
    }
  }

  /**
   * By name assert;
   */
  def byNameAssert(predicate: => Boolean) {
    if (assertEnabled && !predicate) {
      throw new AssertionError
    }
  }

  /**
   * Boolean assert;
   */
  def boolAssert(predicate: Boolean) {
    if (assertEnabled && !predicate) {
      throw new AssertionError
    }
  }

  def main(args: Array[String]) {
    // ugly calling
    myAssert(() => 5 > 3)
    // better calling
    byNameAssert(5 > 3)
    // bool assert
    boolAssert(5 > 3)
    // switch
    assertEnabled = false
    // work well
    byNameAssert(1 / 0 == 0)
    // error
    boolAssert(1 / 0 == 0)
  }
}