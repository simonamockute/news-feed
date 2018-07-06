package com.sm.newsfeed.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.sm.newsfeed.R
import com.sm.newsfeed.dependencyinjection.NewsApplication
import com.sm.newsfeed.models.NewsItem
import com.sm.newsfeed.remote.NewsCategory
import com.sm.newsfeed.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.news_list.*
import javax.inject.Inject

/**
 * An activity representing a list of News. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [NewsDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class NewsListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        setContentView(R.layout.activity_navigation_drawer)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (news_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        newsViewModel.getNews().observe(this, Observer { news ->
            if (news != null) {
                setupRecyclerView(news_list, news)
            }
        })

        newsViewModel.getCategories().observe(this, Observer { categories ->
            if (categories != null) {
                addNavigationItems(categories)
            }
        })
    }

    private fun addNavigationItems(categories: Array<NewsCategory>) {
        nav_view.menu.clear()

        for ((index, category) in categories.withIndex()) {
            nav_view.menu.add(0, index, index, category.title)
        }

        nav_view.menu.setGroupCheckable(0, true, true)

        nav_view.invalidate()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
}
