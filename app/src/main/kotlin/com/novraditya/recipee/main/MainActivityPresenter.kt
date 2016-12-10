package com.novraditya.recipee.main

import com.novraditya.recipee.api.ApiManager
import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 11/30/16.
 */

open class MainActivityPresenter(private val apiManager: ApiManager) {

    fun getRecipes(): Observable<List<Recipe>> = apiManager.getRecipes()

}