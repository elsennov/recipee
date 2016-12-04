package com.novraditya.recipee.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by elsen on 12/08/15.
 */
class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager,
                                        columnNumber: Int,
                                        private val onLoadMore: (Int) -> Unit) : RecyclerView.OnScrollListener() {

    private val LOAD_MORE_TYPE = 0

    // The total number of items in the data set after the last load
    private var previousTotal = 0

    // True if we are still waiting for the last set of data to load.
    private var loading = true

    // The minimum amount of items to have below your current scroll position before loading more.
    private val visibleThreshold = 5

    private var firstVisibleItem: IntArray
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var currentPage: Int = 1

    var nextPageAvailable = false

    init {
        firstVisibleItem = IntArray(columnNumber)
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        visibleItemCount = recyclerView!!.childCount
        totalItemCount = layoutManager.itemCount

        if (layoutManager is StaggeredGridLayoutManager) {
            firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem)
        } else {
            firstVisibleItem[0] = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (firstVisibleItem[0] != -1) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    // Check whether the last item is not the loading layout
                    if (recyclerView.adapter.getItemViewType(previousTotal) != LOAD_MORE_TYPE) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
            }

            LogUtils.debug("loading", if (loading) "true" else "false" )
            LogUtils.debug("next page available", if (nextPageAvailable) "true" else "false" )
            LogUtils.debug("item view type prev total", recyclerView.adapter.getItemViewType(previousTotal-1).toString())
            LogUtils.debug("prev total", previousTotal.toString())
            LogUtils.debug("total item count", totalItemCount.toString())
            LogUtils.debug("visible item count", visibleItemCount.toString() )
            LogUtils.debug("first visible item", firstVisibleItem[0].toString() )


            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem[0] + visibleThreshold
                && nextPageAvailable) {
                currentPage++
                onLoadMore(currentPage)
                loading = true
            }
        }
    }

    fun resetState() {
        this.currentPage = 1
        this.previousTotal = 0
        this.loading = true
    }

    fun resetToPreviousState() {
        this.currentPage--
        this.loading = false
    }

}
