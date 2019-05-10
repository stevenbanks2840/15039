package com.ftresearch.cakes

import com.ftresearch.cakes.di.DaggerRootComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class CakesApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRootComponent.builder()
            .application(this)
            .build()
    }
}
