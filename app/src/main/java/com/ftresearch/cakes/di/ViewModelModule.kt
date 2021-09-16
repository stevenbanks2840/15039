package com.ftresearch.cakes.di

import androidx.lifecycle.ViewModel
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.di.viewmodelfactory.ViewModelKey
import com.ftresearch.cakes.ui.cakes.CakesViewModel
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    fun providesCakesViewModel(
        getCakesUseCase: GetCakesUseCase,
        dispatcherProvider: DispatcherProvider
    ) = CakesViewModel(getCakesUseCase, dispatcherProvider) // DaggerMock only support @Provides

    @Provides
    @IntoMap
    @ViewModelKey(CakesViewModel::class)
    fun bindCakesViewModel(viewModel: CakesViewModel): ViewModel = viewModel
}
