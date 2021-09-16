package com.ftresearch.cakes.di

import com.ftresearch.cakes.ui.cakedetail.CakeDetailFragment
import com.ftresearch.cakes.ui.cakes.CakesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun cakesActivity(): CakesFragment

    @ContributesAndroidInjector
    abstract fun cakeDetailActivity(): CakeDetailFragment
}
