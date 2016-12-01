package com.novraditya.recipee

import android.app.Application
import com.novraditya.recipee.utils.LogUtils

/**
 * Created by elsennovraditya on 11/30/16.
 */
class MyApplication : Application() {

    companion object {
        lateinit var recipeeComponent: RecipeeComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initLogging()
    }

    private fun initDagger() {
        recipeeComponent = DaggerRecipeeComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private fun initLogging() {
        LogUtils.enableLogging(enableLogging = true, logTag = "Recipee")
    }

}