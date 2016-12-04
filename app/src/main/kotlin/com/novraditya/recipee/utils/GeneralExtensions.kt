package com.novraditya.recipee.utils

import android.content.Context
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.novraditya.recipee.R
import org.jetbrains.anko.connectivityManager

/**
 * Detect whether it is connected to internet or not
 */
fun Context.isNetworkAvailable(): Boolean {
    return this.connectivityManager.activeNetworkInfo?.isConnected ?: false
}

/**
 * Build gson instance using lower case with underscores policy
 */
fun Any.toJsonString(): String {
    return buildGson().toJson(this)
}

fun TextView.simpleMatchParentInit(text: String): TextView {
    this.text = text
    layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = resources.getDimensionPixelSize(R.dimen.activity_vertical_margin)
    return this
}

fun RecyclerView.Adapter<out RecyclerView.ViewHolder>.handleNotifyDataSetChanged() {
    Handler().post { notifyDataSetChanged() }
}
