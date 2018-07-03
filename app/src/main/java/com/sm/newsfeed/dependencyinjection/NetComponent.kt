package com.sm.newsfeed.dependencyinjection

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface NetComponent {
//inject here
}
