package com.ftresearch.cakes.di

import com.ftresearch.cakes.ui.cakedetail.CakeDetailFragment
import com.ftresearch.cakes.ui.cakes.CakesFragment
import com.ftresearch.cakes.ui.cakesearch.CakeSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun cakesFragment(): CakesFragment

    @ContributesAndroidInjector
    abstract fun cakeDetailFragment(): CakeDetailFragment

    @ContributesAndroidInjector
    abstract fun cakeSearchFragment(): CakeSearchFragment
}
