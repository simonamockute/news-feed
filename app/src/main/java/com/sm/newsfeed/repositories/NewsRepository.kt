package com.sm.newsfeed.repositories

import android.arch.lifecycle.MutableLiveData
import com.sm.newsfeed.remote.NewsItem
import com.sm.newsfeed.remote.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: NewsService) {
    private val forecast = MutableLiveData<Array<NewsItem>>()
    val error: MutableLiveData<String?> = MutableLiveData()

    fun getNews(): MutableLiveData<Array<NewsItem>> {
        apiService.getNews()
            .enqueue(object : Callback<Array<NewsItem>> {
                override fun onFailure(call: Call<Array<NewsItem>>?, t: Throwable?) {
                    if (t != null) {
                        error.value = t.localizedMessage
                    }
                }

                override fun onResponse(
                    call: Call<Array<NewsItem>>?,
                    response: Response<Array<NewsItem>>?
                ) {
                    forecast.value = response?.body()
                }
            })
        return forecast
    }
}