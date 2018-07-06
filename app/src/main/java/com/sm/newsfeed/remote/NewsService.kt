package com.sm.newsfeed.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("news/{category}/en/my?format=json")
    fun getNews(@Path("category") category: String): Call<Array<NewsResponseItem>>
}