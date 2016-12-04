package com.novraditya.recipee.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by elsennovraditya on 11/29/16.
 */

/**
 * Build gson instance using lower case with underscores policy
 */
fun buildGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}
