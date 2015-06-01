package com.stone.tools.test.food.db

import com.stone.tools.test.food.Food

trait FoodCategories {
  case class FoodCategory(name: String, foods: List[Food])

  def allCategories: List[FoodCategory]
}