package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.ui.cakes.CakesViewState.Loading
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase.GetCakesResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class CakesViewModel @Inject constructor(
    private val getCakesUseCase: GetCakesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val cakes: LiveData<CakesViewState> get() = cakesResource

    private val cakesResource = MutableLiveData<CakesViewState>()

    init {
        loadCakes()
    }

    fun refresh() = loadCakes()

    private fun loadCakes() {
        updateState(Loading)

        viewModelScope.launch(dispatcherProvider.io) {
            val newState = when (val result = getCakesUseCase.getCakes()) {
                is GetCakesResult.Success -> CakesViewState.Success(result.cakes)
                is GetCakesResult.Error -> CakesViewState.Error(result.exception)
            }

            updateState(newState)
        }
    }

    private fun updateState(state: CakesViewState) = cakesResource.postValue(state)
}
