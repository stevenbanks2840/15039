package com.ftresearch.cakes

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.ftresearch.cakes.di.DaggerRootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.sync.CakeSyncRepository
import com.ftresearch.cakes.sync.CakeWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class CakesApplication : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var cakeSyncRepository: CakeSyncRepository

    @Inject
    lateinit var cakeWorkerFactory: CakeWorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRootComponent.builder()
            .application(this)
            .viewModelModule(ViewModelModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        cakeSyncRepository.startSync()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = DelegatingWorkerFactory()
        factory.addFactory(cakeWorkerFactory)

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(factory)
            .build()
    }
}
