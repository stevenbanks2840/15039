package com.ftresearch.cakes.di

import com.ftresearch.cakes.CakesApplication
import com.ftresearch.cakes.di.viewmodelfactory.ViewModelFactoryModule
import com.ftresearch.cakes.ui.cakes.CakesActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        RootModule::class,
        RestModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface RootComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: CakesApplication)

    fun inject(activity: CakesActivity)

    @Component.Builder
    interface Builder {

        fun build(): RootComponent

        fun viewModelModule(module: ViewModelModule): Builder

        @BindsInstance
        fun application(application: CakesApplication): Builder
    }
}
