package com.ftresearch.cakes.di

import com.ftresearch.cakes.ui.cakedetail.CakeDetailFragment
import com.ftresearch.cakes.ui.cakes.CakesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun cakesFragment(): CakesFragment

    @ContributesAndroidInjector
    abstract fun cakeDetailFragment(): CakeDetailFragment
}
