package com.novraditya.recipee.main

import com.novraditya.recipee.MyApplication
import com.novraditya.recipee.R
import com.novraditya.recipee.utils.LogUtils
import com.trello.navi2.Event
import com.trello.navi2.NaviComponent
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import io.reactivex.Observable
import kotlin.concurrent.thread

class MainActivity : NaviAppCompatActivity() {

    val TAG: String = MainActivity::class.java.simpleName
    val naviComponent: NaviComponent = this
    val mainActivityPresenter: MainActivityPresenter by lazy { MyApplication.recipeeComponent.provideMainActivityPresenter() }

    init {
        initLayout()
        initRecipe()
    }

    private fun initLayout() {
        RxNavi
            .observe(naviComponent, Event.CREATE)
            .doOnNext({ setContentView(R.layout.activity_main) })
            .takeUntil(RxNavi.observe(naviComponent, Event.DESTROY))
            .doOnTerminate {  }
            .subscribe(
                { LogUtils.debug(TAG, "onNext in initLayout") },
                { LogUtils.error(TAG, "onError in initLayout", it) },
                { LogUtils.debug(TAG, "onComplete in initLayout") }
            )
    }

    private fun initRecipe() {
        RxNavi
            .observe(naviComponent, Event.CREATE)
            .flatMap { mainActivityPresenter.getRecipes() }
            .flatMap { Observable.fromIterable(it) }
            .takeUntil(RxNavi.observe(naviComponent, Event.DESTROY))
            .subscribe(
                { LogUtils.debug(TAG, it.name) },
                { LogUtils.error(TAG, "onError in initRecipe", it) },
                { LogUtils.debug(TAG, "onComplete in initRecipe") }
            )
    }

}
