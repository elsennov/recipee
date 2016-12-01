package com.novraditya.recipee

import com.novraditya.recipee.api.ApiModule
import com.novraditya.recipee.api.RetrofitModule
import com.novraditya.recipee.main.MainActivityPresenter
import com.novraditya.recipee.main.MainModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by elsennovraditya on 11/30/16.
 */
@Singleton
@Component(modules = arrayOf(
    ApplicationModule::class,
    RetrofitModule::class,
    ApiModule::class,
    MainModule::class
))
interface RecipeeComponent {
    fun provideMainActivityPresenter(): MainActivityPresenter
}
