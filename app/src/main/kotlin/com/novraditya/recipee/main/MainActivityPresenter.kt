package com.novraditya.recipee.main

import com.novraditya.recipee.BasePresenter
import com.novraditya.recipee.api.ApiManager
import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 11/30/16.
 */

open class MainActivityPresenter(private val apiManager: ApiManager) : BasePresenter() {

    fun getRecipes(): Observable<List<Recipe>> {
        return handleRequestError(
            apiManager.getRecipes(),
            { isRecipesEmpty(it) }
        )
    }

    fun isRecipesEmpty(recipes: List<Recipe>?): Boolean {
        return recipes == null || recipes.isEmpty()
    }

}