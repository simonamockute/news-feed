package com.sm.newsfeed.ui

import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerViewEmptySupportObserver(
    private val recyclerView: RecyclerView,
    private val emptyView: View
) : RecyclerView.AdapterDataObserver() {

    override fun onChanged() {
        super.onChanged()
        updateEmptyViewVisibility()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        updateEmptyViewVisibility()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        updateEmptyViewVisibility()
    }

    fun updateEmptyViewVisibility() {
        val isEmpty = recyclerView.adapter.itemCount == 0
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }
}
