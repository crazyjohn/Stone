package com.stone.tools.test.food.db

import com.stone.tools.test.food.Food
import com.stone.tools.test.food.Recipe

abstract class DataBase extends FoodCategories {
  def allFoods: List[Food]

  def allRecipes: List[Recipe]

  def foodNamed(name: String) = allFoods.find { f => f.name == name }
}