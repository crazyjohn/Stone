package com.stone.scalaer.rational

class Rational(n: Int, d: Int) {
  // add require
  require(d != 0)
  val number: Int = n
  val denom: Int = d

  // helper constuctor
  def this(n: Int) = this(n, 1)
  
  //  override def toString():String = {
  //    n + "/" + d
  //  }
  override def toString = number + "/" + denom
  
  def add(that: Rational): Rational = new Rational(n * that.denom + that.number * d, d * that.denom)
  
  def +(that: Rational): Rational = this.add(that)
  
  def +(that: Int): Rational = new Rational(n + that * d, d)
}