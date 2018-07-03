package com.sm.newsfeed.repositories

import com.sm.newsfeed.remote.NewsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: NewsService) {

}