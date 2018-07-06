package com.sm.newsfeed.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sm.newsfeed.R
import com.sm.newsfeed.dependencyinjection.GlideApp
import com.sm.newsfeed.dependencyinjection.NewsApplication
import com.sm.newsfeed.models.NewsItem
import com.sm.newsfeed.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.news_list.*
import kotlinx.android.synthetic.main.news_list_content.view.*
import javax.inject.Inject

/**
 * An activity representing a list of News. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [NewsDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class NewsListActivity : AppCompatActivity() {

    @Inject
    lateinit var newsViewModel: NewsViewModel

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as NewsApplication).getApplicationComponent().inject(this)

        setContentView(R.layout.activity_news_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (news_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        newsViewModel.getNews().observe(this, Observer { news ->
            if (news != null) {
                setupRecyclerView(news_list, news)
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, values: Array<NewsItem>) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            this,
            values,
            twoPane
        )

        val viewManager = LinearLayoutManager(this)
        recyclerView.layoutManager = viewManager

        val dividerItemDecoration = DividerItemDecoration(
            news_list.context,
            viewManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

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
                    val intent = Intent(v.context, NewsDetailActivity::class.java).apply {
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
}
