package com.sm.newsfeed.dependencyinjection

import com.sm.newsfeed.ui.NewsListActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface NetComponent {
    fun inject(activity: NewsListActivity)
}
