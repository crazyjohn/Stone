package com.stone.tools.test.food

object SimpleBrowser {

  def recipesUsing(food: Food) = SimpleDatabase.allRecipes.filter { recipe => recipe.ingredients.contains(food) }

  def displayCategory(category: SimpleDatabase.FoodCategory) = println(category)

}