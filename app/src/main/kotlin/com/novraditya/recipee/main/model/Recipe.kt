package com.novraditya.recipee.main.model

/**
 * Created by elsennovraditya on 11/29/16.
 */
data class Recipe(
    var id: String = "",
    var name: String = "",
    var ingredient: List<Ingredient> = emptyList()
)
