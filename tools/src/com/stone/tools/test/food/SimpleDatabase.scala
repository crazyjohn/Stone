package com.stone.tools.test.food

object SimpleDatabase {

  def allFoods = List(Apple, Orange, Cream, Sugar)

  def foodNamed(name: String): Option[Food] = allFoods.find { (food: Food) => food.name == name }

  def allRecipes: List[Recipe] = List(FruitSalad)

  case class FoodCategory(name: String, foods: List[Food])

  private var categories = List(FoodCategory("fruits", List(Apple, Orange)), FoodCategory("misc", List(Cream, Sugar)))

  def allCategories = categories

}