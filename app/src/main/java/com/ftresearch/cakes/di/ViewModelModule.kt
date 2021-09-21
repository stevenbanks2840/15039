package com.ftresearch.cakes.di

import androidx.lifecycle.ViewModel
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.di.viewmodelfactory.ViewModelKey
import com.ftresearch.cakes.domain.usecase.GetCakeSyncStateUseCase
import com.ftresearch.cakes.domain.usecase.GetCakesUseCase
import com.ftresearch.cakes.domain.usecase.SearchCakesUseCase
import com.ftresearch.cakes.domain.usecase.StartCakeSyncUseCase
import com.ftresearch.cakes.presentation.cakes.CakesViewModel
import com.ftresearch.cakes.presentation.cakesearch.CakeSearchViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    fun providesCakesViewModel(
        getCakesUseCase: GetCakesUseCase,
        getCakeSyncStateUseCase: GetCakeSyncStateUseCase,
        startCakeSyncUseCase: StartCakeSyncUseCase,
    ) = CakesViewModel(getCakesUseCase, getCakeSyncStateUseCase, startCakeSyncUseCase) // DaggerMock only support @Provides

    @Provides
    fun providesCakesSearchViewModel(
        searchCakesUseCase: SearchCakesUseCase,
        dispatcherProvider: DispatcherProvider
    ) = CakeSearchViewModel(searchCakesUseCase, dispatcherProvider) // DaggerMock only support @Provides

    @Provides
    @IntoMap
    @ViewModelKey(CakesViewModel::class)
    fun bindCakesViewModel(viewModel: CakesViewModel): ViewModel = viewModel

    @Provides
    @IntoMap
    @ViewModelKey(CakeSearchViewModel::class)
    fun bindCakesSearchViewModel(viewModel: CakeSearchViewModel): ViewModel = viewModel
}
