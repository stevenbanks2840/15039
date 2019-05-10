package com.ftresearch.cakes.di

import com.ftresearch.cakes.CakesDispatcherProvider
import com.ftresearch.cakes.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
abstract class RootModule {

    @Binds
    abstract fun bindsDispatcherProvider(dispatcherProvider: CakesDispatcherProvider): DispatcherProvider
}
