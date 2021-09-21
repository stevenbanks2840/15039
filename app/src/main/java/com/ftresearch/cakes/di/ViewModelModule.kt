package com.ftresearch.cakes.di

import androidx.lifecycle.ViewModel
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.di.viewmodelfactory.ViewModelKey
import com.ftresearch.cakes.ui.cakes.CakesViewModel
import com.ftresearch.cakes.ui.cakesearch.CakesSearchViewModel
import com.ftresearch.cakes.usecase.GetCakesUseCase
import com.ftresearch.cakes.usecase.PrePopulateUseCase
import com.ftresearch.cakes.usecase.SearchCakesUseCase
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    fun providesCakesViewModel(
        getCakesUseCase: GetCakesUseCase,
        prePopulateUseCase: PrePopulateUseCase,
        dispatcherProvider: DispatcherProvider
    ) = CakesViewModel(getCakesUseCase, prePopulateUseCase, dispatcherProvider) // DaggerMock only support @Provides

    @Provides
    fun providesCakesSearchViewModel(
        searchCakesUseCase: SearchCakesUseCase,
        dispatcherProvider: DispatcherProvider
    ) = CakesSearchViewModel(searchCakesUseCase, dispatcherProvider) // DaggerMock only support @Provides

    @Provides
    @IntoMap
    @ViewModelKey(CakesViewModel::class)
    fun bindCakesViewModel(viewModel: CakesViewModel): ViewModel = viewModel

    @Provides
    @IntoMap
    @ViewModelKey(CakesSearchViewModel::class)
    fun bindCakesSearchViewModel(viewModel: CakesSearchViewModel): ViewModel = viewModel
}
