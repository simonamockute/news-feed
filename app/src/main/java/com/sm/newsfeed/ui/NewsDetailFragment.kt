package com.sm.newsfeed.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.sm.newsfeed.R
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.news_detail.*

/**
 * A fragment representing a single News detail screen.
 * This fragment is either contained in a [NewsListActivity]
 * in two-pane mode (on tablets) or a [NewsDetailActivity]
 * on handsets.
 */
class NewsDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_URL)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                url = it.getString(ARG_ITEM_URL)
                activity?.toolbar_layout?.title = it.getString(ARG_ITEM_TITLE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_detail.webViewClient = WebViewClient()

        news_detail.loadUrl(url)

    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_URL = "item_url"
        const val ARG_ITEM_TITLE = "item_title"
    }
}
