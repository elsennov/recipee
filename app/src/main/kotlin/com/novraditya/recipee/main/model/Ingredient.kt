package com.novraditya.recipee.main.model

data class Ingredient(
    var name: String = "",
    var unit: String = "",
    var amount: Float = 0f,
    var addition: Addition = Addition()
)
