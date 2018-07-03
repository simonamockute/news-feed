package com.sm.weatherapp.dependencyinjection

import android.app.Application
import com.sm.newsfeed.repositories.NewsRepository
import com.sm.newsfeed.viewmodels.NewsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideNewsViewModel(newsRepository: NewsRepository): NewsViewModel {
        return NewsViewModel(newsRepository)
    }
}
