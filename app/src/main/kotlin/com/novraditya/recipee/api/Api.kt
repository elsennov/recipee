package com.novraditya.recipee.api

import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by elsennovraditya on 11/30/16.
 */

interface Api {

    @GET("recipes")
    fun retrieveRecipes(): Observable<List<Recipe>>

}
