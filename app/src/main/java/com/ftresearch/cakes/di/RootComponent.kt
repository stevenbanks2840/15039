package com.ftresearch.cakes.di

import com.ftresearch.cakes.CakesApplication
import com.ftresearch.cakes.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        RestModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface RootComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: CakesApplication)

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build(): RootComponent

        @BindsInstance
        fun application(application: CakesApplication): Builder
    }
}
