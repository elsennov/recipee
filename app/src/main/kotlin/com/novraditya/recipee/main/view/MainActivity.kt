package com.novraditya.recipee.main.view

import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding.view.RxView
import com.novraditya.recipee.MyApplication
import com.novraditya.recipee.R
import com.novraditya.recipee.api.error.NetworkError
import com.novraditya.recipee.api.error.NoInternetError
import com.novraditya.recipee.api.error.ResultEmptyError
import com.novraditya.recipee.main.MainActivityPresenter
import com.novraditya.recipee.main.model.ingredients
import com.novraditya.recipee.main.model.recipe
import com.novraditya.recipee.utils.EndlessRecyclerViewScrollListener
import com.novraditya.recipee.utils.LogUtils
import com.trello.navi2.Event
import com.trello.navi2.NaviComponent
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.toast

class MainActivity : NaviAppCompatActivity() {

    private val tag: String = MainActivity::class.java.simpleName
    private val naviComponent: NaviComponent = this
    private val mainActivityPresenter: MainActivityPresenter by lazy {
        MyApplication.recipeeComponent.provideMainActivityPresenter()
    }
    private val endlessScrollListener: EndlessRecyclerViewScrollListener by lazy {
        EndlessRecyclerViewScrollListener(
            recipesContainer.layoutManager, 1, { loadMoreRecipes() }
        )
    }

    init {
        initLayout()
        initRecipe()
        initRecipeBuilder()
        initButtonInlined { doSomething() }
    }

    private fun initLayout() {
        RxNavi
            .observe(naviComponent, Event.CREATE)
            .doOnNext { setContentView(R.layout.activity_main) }
            .takeUntil(RxNavi.observe(naviComponent, Event.DESTROY))
            .subscribe(
                { LogUtils.debug(tag, "onNext in initLayout") },
                { LogUtils.error(tag, "onError in initLayout", it) },
                { LogUtils.debug(tag, "onComplete in initLayout") }
            )
    }

    private fun initRecipe() {
        RxNavi
            .observe(naviComponent, Event.CREATE)
            .observeOn(Schedulers.io())
            .flatMap { mainActivityPresenter.getRecipes() }
            .filter { it is List && !it.isEmpty() }
            .map { MainAdapter(mutableListOf(*it.toTypedArray())) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { recipesContainer.layoutManager = LinearLayoutManager(this) }
            .doOnNext { recipesContainer.addOnScrollListener(endlessScrollListener) }
            .doOnNext { endlessScrollListener.nextPageAvailable = true }
            .takeUntil(RxNavi.observe(naviComponent, Event.DESTROY))
            .subscribe(
                { recipesContainer.adapter = it },
                {
                    LogUtils.error(tag, "onError in initRecipe", it)
                    when (it) {
                        is ResultEmptyError -> toast(R.string.error_recipes_empty)
                        is NetworkError -> toast(R.string.error_network)
                        is NoInternetError -> toast(R.string.error_no_internet)
                        is UnknownError -> toast(R.string.error_retrieving_recipes)
                    }
                },
                { LogUtils.debug(tag, "onComplete in initRecipe") }
            )
    }

    private fun loadMoreRecipes() {
        LogUtils.debug(tag, "loadMoreRecipes")

        mainActivityPresenter
            .getRecipes()
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { recipesContainer.getMainAdapter().showLoadMore() }
            .doOnNext { recipesContainer.getMainAdapter().hideLoadMore() }
            .subscribe(
                {
                    LogUtils.debug(tag, "onNext in loadMoreRecipes")
                    if (it != null && !it.isEmpty()) {
                        (recipesContainer.adapter as MainAdapter).addRecipes(it)
                    } else {
                        endlessScrollListener.nextPageAvailable = false
                        endlessScrollListener.resetToPreviousState()
                    }
                },
                {
                    LogUtils.error(tag, "onError in loadMoreRecipes", it)
                    endlessScrollListener.resetToPreviousState()
                    toast(R.string.error_retrieving_recipes)
                },
                { LogUtils.debug(tag, "onComplete in loadMoreRecipes") }
            )
    }

    private fun initRecipeBuilder() {
        val recipe = recipe {
            id = "123"
            name = "Elsen"
            ingredients {
                com.novraditya.recipee.main.model.ingredient {
                    name = "Sugar"
                    unit = "kg"
                    amount = 2.0f
                    com.novraditya.recipee.main.model.addition {
                        name = "hahaha"
                    }
                }
                com.novraditya.recipee.main.model.ingredient {
                    name = "Salt"
                    unit = "kg"
                    amount = 1.0f
                    com.novraditya.recipee.main.model.addition {
                        name = "hehehe"
                    }
                }
            }
        }

        LogUtils.debug(tag, "onNext in initLayout: ${recipe.name}")
        LogUtils.debug(tag, "onNext in initLayout: ${recipe.ingredient[0].name}")
        LogUtils.debug(tag, "onNext in initLayout: ${recipe.ingredient[0].addition.info}")
    }

    inline private fun initButtonInlined(doSomething: () -> Unit) {
        doSomething()
    }

    private fun doSomething() {
        RxNavi
            .observe(naviComponent, Event.CREATE)
            .switchMap {
                RxJavaInterop.toV2Observable(
                    RxView
                        .clicks(testButton)
                        .map { Object() }
                )
            }
            .subscribe { browse("https://www.prismapp.io") }
    }

}
