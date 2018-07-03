package com.sm.newsfeed.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sm.newsfeed.remote.NewsItem
import com.sm.newsfeed.repositories.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getNews(query: String): MutableLiveData<Array<NewsItem>> =
        newsRepository.getNews()
}