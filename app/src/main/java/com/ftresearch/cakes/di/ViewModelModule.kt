package com.ftresearch.cakes.di

import androidx.lifecycle.ViewModel
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.di.viewmodelfactory.ViewModelKey
import com.ftresearch.cakes.services.cake.CakeService
import com.ftresearch.cakes.ui.cakes.CakesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    fun providesCakesViewModel(cakeService: CakeService, dispatcherProvider: DispatcherProvider) =
            CakesViewModel(cakeService, dispatcherProvider) // DaggerMock only support @Provides

    @Provides
    @IntoMap
    @ViewModelKey(CakesViewModel::class)
    fun bindCakesViewModel(viewModel: CakesViewModel): ViewModel = viewModel
}
