package com.novraditya.recipee.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by elsennovraditya on 6/11/16.
 */
class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headerInterceptedRequest = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(headerInterceptedRequest)
    }

}
