package com.stone.tools.test.traits

trait CharSequence {
  
  def charAt(index: Int): Char
  
  def length: Int
  
  def subSequence(start: Int, end: Int): CharSequence
  
  def toString(): String
  
}