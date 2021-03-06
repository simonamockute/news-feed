package com.sm.newsfeed.dependencyinjection

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sm.newsfeed.BuildConfig
import com.sm.newsfeed.remote.NewsCategoriesService
import com.sm.newsfeed.remote.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024L
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    @Named("newsApi")
    fun provideNewsRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("newsCategoriesApi")
    fun provideNewsCategoriesRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_CATEGORIES_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteNewsService(@Named("newsApi") retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Provides
    @Singleton
    fun provideRemoteNewsCategoriesService(@Named("newsCategoriesApi") retrofit: Retrofit): NewsCategoriesService =
        retrofit.create(NewsCategoriesService::class.java)
}
