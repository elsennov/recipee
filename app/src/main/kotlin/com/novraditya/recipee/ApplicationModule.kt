package com.novraditya.recipee

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by elsennovraditya on 11/30/16.
 */
@Module
class ApplicationModule(val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

}