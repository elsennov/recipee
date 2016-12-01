package com.novraditya.recipee.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by elsennovraditya on 11/30/16.
 */

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideApiManager(api: Api): ApiManager {
        return ApiManager(api)
    }

}