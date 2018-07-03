package com.sm.newsfeed.remote

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("news/featured/en/my?format=json")
    fun getNews(): Call<Array<NewsItem>>
}