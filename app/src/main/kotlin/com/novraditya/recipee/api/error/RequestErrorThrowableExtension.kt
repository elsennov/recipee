package com.novraditya.recipee.api.error

import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by elsennovraditya on 12/13/16.
 */

fun Throwable.isRequestErrorNoInternet(): Boolean {
    return this is UnknownHostException
}

fun Throwable.isRequestErrorNetwork(): Boolean {
    return this is IOException
        || (this.message != null && this.message?.contains(other = "NetworkNotAvailable") ?: false)
}
