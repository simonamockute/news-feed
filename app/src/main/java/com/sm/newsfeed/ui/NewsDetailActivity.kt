package com.sm.newsfeed.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.sm.newsfeed.R
import kotlinx.android.synthetic.main.activity_news_detail.*

/**
 * An activity representing a single News detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [NewsListActivity].
 */
class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        NewsDetailFragment.ARG_ITEM_URL,
                        intent.getStringExtra(NewsDetailFragment.ARG_ITEM_URL)
                    )
                    putString(
                        NewsDetailFragment.ARG_ITEM_TITLE,
                        intent.getStringExtra(NewsDetailFragment.ARG_ITEM_TITLE)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.news_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpTo(this, Intent(this, NewsListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
