package com.novraditya.recipee.api

import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 11/30/16.
 *
 * TODO : Every interceptor for an API call should be in this class
 */
class ApiManager(private val api: Api) {

    fun getRecipes(): Observable<List<Recipe>> {
        return api.retrieveRecipes()
    }

}
