package com.ftresearch.cakes

import com.ftresearch.cakes.di.DaggerRootComponent
import com.ftresearch.cakes.di.ViewModelModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CakesApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRootComponent.builder()
            .application(this)
            .viewModelModule(ViewModelModule())
            .build()
    }
}
