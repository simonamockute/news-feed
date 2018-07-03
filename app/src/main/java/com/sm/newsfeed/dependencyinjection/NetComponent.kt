package com.sm.weatherapp.dependencyinjection

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface NetComponent {
//inject here
}
