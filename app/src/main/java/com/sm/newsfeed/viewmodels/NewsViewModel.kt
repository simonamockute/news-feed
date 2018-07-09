package com.sm.newsfeed.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
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
    val selectedCategory = MutableLiveData<String>()
    val categories: MutableLiveData<Array<NewsCategory>>

    val error = newsRepository.error

    init {
        selectedCategory.value = "featured"
        categories = newsRepository.getCategories()
    }

    val news: LiveData<Array<NewsItem>> =
        Transformations.switchMap(selectedCategory) { category ->
            newsRepository.getNews(category)
        }
}