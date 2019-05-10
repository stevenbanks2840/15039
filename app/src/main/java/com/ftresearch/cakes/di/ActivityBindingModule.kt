package com.ftresearch.cakes.di

import com.ftresearch.cakes.ui.cakedetail.CakeDetailActivity
import com.ftresearch.cakes.ui.cakes.CakesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun cakesActivity(): CakesActivity

    @ContributesAndroidInjector
    abstract fun cakeDetailActivity(): CakeDetailActivity
}
