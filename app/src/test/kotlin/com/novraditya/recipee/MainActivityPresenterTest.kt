package com.novraditya.recipee

import com.novraditya.recipee.api.Api
import com.novraditya.recipee.api.ApiManager
import com.novraditya.recipee.main.MainActivityPresenter
import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

/**
 * Created by elsennovraditya on 12/6/16.
 */

class MainActivityPresenterTest {

    @Test fun testGetRecipesReturnListOfRecipe() {
        val api = Mockito.mock(Api::class.java)
        val apiManager = ApiManager(api)
        val mainActivityPresenter = MainActivityPresenter(apiManager)
        val recipe = Mockito.mock(Recipe::class.java)
        val recipeList = listOf(recipe)

        `when`(api.retrieveRecipes()).then { Observable.just(recipeList) }

        mainActivityPresenter.getRecipes().test().assertResult(recipeList)
        mainActivityPresenter.getRecipes().test().assertNoErrors()
        mainActivityPresenter.getRecipes().test().assertComplete()
    }

    @Test fun testGetRecipesReturnError() {
        val api = Mockito.mock(Api::class.java)
        val apiManager = ApiManager(api)
        val mainActivityPresenter = MainActivityPresenter(apiManager)
        val recipe = Mockito.mock(Recipe::class.java)
        val throwable = Throwable("Error in retrieving receipt")

        `when`(api.retrieveRecipes()).then { Observable.error<Throwable>(throwable) }

        mainActivityPresenter.getRecipes().test().assertError(throwable)
    }

}
