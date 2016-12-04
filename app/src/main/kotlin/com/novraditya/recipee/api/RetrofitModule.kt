package com.novraditya.recipee.api

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.novraditya.recipee.BuildConfig
import com.novraditya.recipee.utils.LogUtils
import com.novraditya.recipee.utils.buildGson
import com.novraditya.recipee.utils.isNetworkAvailable
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by elsennovraditya on 9/9/16.
 */

@Module
class RetrofitModule() {

    private val HTTP_CACHE = "http-cache"
    private val MAX_CACHE_SIZE = (10 * 1024 * 1024).toLong() // 10 MB

    @Singleton
    @Provides
    fun provideOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(buildHttpLoggingInterceptor())
            .addInterceptor(buildOfflineCacheInterceptor(application))
            .addInterceptor(HeaderInterceptor())
            .cache(buildCache(application))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .build()
    }

    private fun buildHttpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (LogUtils.enableLogging) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    private fun buildOfflineCacheInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!context.isNetworkAvailable()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }

    private fun buildCache(context: Context): Cache {
        return Cache(File(context.cacheDir, HTTP_CACHE), MAX_CACHE_SIZE)
    }

}
