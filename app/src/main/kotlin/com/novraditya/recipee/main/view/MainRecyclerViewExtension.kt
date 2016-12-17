package com.novraditya.recipee.main.view

import android.support.v7.widget.RecyclerView

/**
 * Created by elsennovraditya on 12/16/16.
 */

fun RecyclerView.getMainAdapter(): MainAdapter {
    return this.adapter as MainAdapter
}
