package com.sm.newsfeed.dependencyinjection

import android.app.Application
import javax.inject.Inject

class NewsApplication : Application() {

    @Inject
    lateinit var component: NetComponent;

    override fun onCreate() {
        super.onCreate()

        component = DaggerNetComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build();
    }

    fun getApplicationComponent(): NetComponent {
        return component
    }
}
