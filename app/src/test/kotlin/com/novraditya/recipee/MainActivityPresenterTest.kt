package com.novraditya.recipee

import com.novraditya.recipee.api.Api
import com.novraditya.recipee.api.ApiManager
import com.novraditya.recipee.api.error.NetworkError
import com.novraditya.recipee.api.error.NoInternetError
import com.novraditya.recipee.api.error.ResultEmptyError
import com.novraditya.recipee.api.error.UnknownError
import com.novraditya.recipee.main.MainActivityPresenter
import com.novraditya.recipee.main.model.Recipe
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by elsennovraditya on 12/6/16.
 */

class MainActivityPresenterTest {

    val api by lazy { Mockito.mock(Api::class.java) }
    val apiManager by lazy { ApiManager(api) }
    val mainActivityPresenter by lazy { MainActivityPresenter(apiManager) }

    @Test fun testGetRecipesReturnListOfRecipe() {
        val recipe = Mockito.mock(Recipe::class.java)
        val recipeList = listOf(recipe)

        `when`(api.retrieveRecipes()).then { Observable.just(recipeList) }

        mainActivityPresenter.getRecipes().test().assertResult(recipeList)
        mainActivityPresenter.getRecipes().test().assertNoErrors()
        mainActivityPresenter.getRecipes().test().assertComplete()
    }

    @Test fun testGetRecipesReturnResultEmptyError() {
        val recipes = listOf<Recipe>()

        `when`(api.retrieveRecipes()).then { Observable.just(recipes) }

        mainActivityPresenter.getRecipes().test().assertError(ResultEmptyError::class.java)
        mainActivityPresenter.getRecipes().test().assertTerminated()
    }

    @Test fun testGetRecipesReturnUnknownError() {
        val throwable = Throwable("Error in retrieving receipt")

        `when`(api.retrieveRecipes()).then { Observable.error<Throwable>(throwable) }

        mainActivityPresenter.getRecipes().test().assertError(UnknownError::class.java)
        mainActivityPresenter.getRecipes().test().assertTerminated()
    }

    @Test fun testGetRecipesReturnNetworkError() {
        val throwable = Throwable("NetworkNotAvailable")

        `when`(api.retrieveRecipes()).then { Observable.error<IOException>(throwable) }

        mainActivityPresenter.getRecipes().test().assertError(NetworkError::class.java)
        mainActivityPresenter.getRecipes().test().assertTerminated()
    }

    @Test fun testGetRecipesReturnNetworkError2() {
        val throwable = IOException()

        `when`(api.retrieveRecipes()).then { Observable.error<IOException>(throwable) }

        mainActivityPresenter.getRecipes().test().assertError(NetworkError::class.java)
        mainActivityPresenter.getRecipes().test().assertTerminated()
    }

    @Test fun testGetRecipesReturnNoInternetError() {
        val throwable = UnknownHostException()

        `when`(api.retrieveRecipes()).then { Observable.error<UnknownHostException>(throwable) }

        mainActivityPresenter.getRecipes().test().assertError(NoInternetError::class.java)
        mainActivityPresenter.getRecipes().test().assertTerminated()
    }

}
