package com.novraditya.recipee.api.error

/**
 * Created by elsennovraditya on 12/13/16.
 */

open class RequestError(protected val throwable: Throwable) : Throwable(throwable)
