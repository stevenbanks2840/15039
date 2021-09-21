package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.*
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.ui.cakes.CakesViewState.Loading
import com.ftresearch.cakes.usecase.GetCakesUseCase
import com.ftresearch.cakes.usecase.PrePopulateUseCase
import com.ftresearch.cakes.usecase.PrePopulateUseCase.PrePopulateResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class CakesViewModel @Inject constructor(
    private val getCakesUseCase: GetCakesUseCase,
    private val prePopulateUseCase: PrePopulateUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val cakes: LiveData<List<Cake>> get() = getCakesUseCase.getCakes().asLiveData()

    val viewState: LiveData<CakesViewState> get() = _viewState

    private val _viewState = MutableLiveData<CakesViewState>()

    init {
        loadCakes()
    }

    fun refresh() = loadCakes()

    private fun loadCakes() {
        updateState(Loading)

        viewModelScope.launch(dispatcherProvider.io) {
            val viewState = when (val result = prePopulateUseCase.prepopulateCakes()) {
                is PrePopulateResult.Success -> CakesViewState.PrePopulated
                is PrePopulateResult.Error -> CakesViewState.Error(result.exception)
            }
            updateState(viewState)
        }
    }

    private fun updateState(state: CakesViewState) = _viewState.postValue(state)
}
