package com.sm.newsfeed.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sm.newsfeed.R
import com.sm.newsfeed.dependencyinjection.GlideApp
import com.sm.newsfeed.models.NewsItem
import kotlinx.android.synthetic.main.news_list_content.view.*

class SimpleItemRecyclerViewAdapter(
    private val parentActivity: NewsListActivity,
    private val values: Array<NewsItem>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as NewsItem
            if (twoPane) {
                val fragment = NewsDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(NewsDetailFragment.ARG_ITEM_URL, item.articleUrl)
                        putString(NewsDetailFragment.ARG_ITEM_TITLE, item.title)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.news_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(
                    v.context,
                    NewsDetailActivity::class.java
                ).apply {
                    putExtra(NewsDetailFragment.ARG_ITEM_URL, item.articleUrl)
                    putExtra(NewsDetailFragment.ARG_ITEM_TITLE, item.title)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.timeAgoView.text = item.postAge
        holder.sourceView.text = item.source

        if (!item.imageLink.isEmpty()) {
            GlideApp.with(this.parentActivity).load(item.imageLink).fitCenter()
                .into(holder.imageView)
        }

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.titleText
        val timeAgoView: TextView = view.timeAgoText
        val imageView: ImageView = view.imageView
        val sourceView: TextView = view.sourceText
    }
}