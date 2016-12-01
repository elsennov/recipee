package com.novraditya.recipee.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by elsennovraditya on 11/29/16.
 */

/**
 * Show toast to the user
 */
fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

/**
 * Detect whether it is connected to internet or not
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetworkInfo?.isConnected ?: false
}

/**
 * Build gson instance using lower case with underscores policy
 */
fun buildGson(): Gson {
    return GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}
