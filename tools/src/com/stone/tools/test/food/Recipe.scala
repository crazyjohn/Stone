package com.stone.tools.test.food

class Recipe(
  val name: String,
  val ingredients: List[Food],
  val instructions: String) {

  override def toString = name;

}