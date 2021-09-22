package com.ftresearch.cakes.presentation.cakesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.domain.usecase.SearchCakesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CakeSearchViewModel @Inject constructor(
    private val searchCakesUseCase: SearchCakesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val searchResults: LiveData<List<Cake>> get() = _searchResults

    private val _searchResults = MutableLiveData<List<Cake>>()

    init {
        search("")
    }

    fun search(text: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val results = searchCakesUseCase(text)
            _searchResults.postValue(results)
        }
    }
}
