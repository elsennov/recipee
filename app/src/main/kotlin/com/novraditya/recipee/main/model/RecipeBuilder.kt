package com.novraditya.recipee.main.model

/**
 * Created by elsennovraditya on 12/16/16.
 */

fun recipe(init: Recipe.() -> Unit): Recipe {
    val recipe = Recipe()
    recipe.init()
    return recipe
}

fun ingredients(init: List<Ingredient>.() -> Unit): List<Ingredient> {
    val ingredients = mutableListOf<Ingredient>()
    ingredients.init()
    return ingredients
}

fun ingredient(init: Ingredient.() -> Unit): Ingredient {
    val ingredient = Ingredient()
    ingredient.init()
    return ingredient
}

fun addition(init: Addition.() -> Unit): Addition {
    val addition = Addition()
    addition.init()
    return addition
}
