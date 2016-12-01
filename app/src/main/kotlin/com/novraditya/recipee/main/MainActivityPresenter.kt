package com.novraditya.recipee.main

import com.novraditya.recipee.main.model.Recipe
import com.novraditya.recipee.api.ApiManager
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 11/30/16.
 */

class MainActivityPresenter(private val apiManager: ApiManager) {

    fun getRecipes(): Observable<List<Recipe>> {
        return apiManager.getRecipes()
    }

}