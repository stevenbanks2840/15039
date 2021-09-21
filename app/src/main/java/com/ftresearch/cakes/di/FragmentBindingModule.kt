package com.ftresearch.cakes.di

import com.ftresearch.cakes.presentation.cakedetail.CakeDetailFragment
import com.ftresearch.cakes.presentation.cakes.CakesFragment
import com.ftresearch.cakes.presentation.cakesearch.CakeSearchFragment
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
