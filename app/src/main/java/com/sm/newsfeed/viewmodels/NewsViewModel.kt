package com.sm.newsfeed.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sm.newsfeed.models.NewsItem
import com.sm.newsfeed.remote.NewsCategory
import com.sm.newsfeed.repositories.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getCategories(): MutableLiveData<Array<NewsCategory>> =
        newsRepository.getCategories()

    fun getNews(): MutableLiveData<Array<NewsItem>> =
        newsRepository.getNews()
}