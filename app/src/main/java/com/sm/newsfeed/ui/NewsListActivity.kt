package com.sm.newsfeed.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sm.newsfeed.R
import com.sm.newsfeed.dependencyinjection.NewsApplication
import com.sm.newsfeed.dummy.DummyContent
import com.sm.newsfeed.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.news_list.*
import kotlinx.android.synthetic.main.news_list_content.view.*
import org.w3c.dom.Text
import javax.inject.Inject

/**
 * An activity representing a list of Pings. This activity
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

        setupRecyclerView(news_list)

        newsViewModel.getNews().observe(this, Observer { news -> Log.d("data", news?.joinToString()) })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            this,
            DummyContent.ITEMS,
            twoPane
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: NewsListActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (twoPane) {
                    val fragment = NewsDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(NewsDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.news_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, NewsDetailActivity::class.java).apply {
                        putExtra(NewsDetailFragment.ARG_ITEM_ID, item.id)
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
            holder.titleView.text = item.id
            holder.timeAgoView.text = item.content

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
