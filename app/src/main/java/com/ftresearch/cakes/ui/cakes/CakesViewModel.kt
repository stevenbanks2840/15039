package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.extensions.exhaustive
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase.GetCakesResult.Failure
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase.GetCakesResult.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

class CakesViewModel @Inject constructor(
    private val getCakesUseCase: GetCakesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val cakes: LiveData<Resource<List<Cake>>>
        get() = cakesResource

    private val cakesResource = MutableLiveData<Resource<List<Cake>>>()

    fun init() {
        if (cakes.value?.status in listOf(null, Resource.Status.ERROR)) {
            loadCakes()
        }
    }

    fun refresh() = loadCakes()

    private fun loadCakes() {
        cakesResource.postValue(Resource(Resource.Status.LOADING))

        viewModelScope.launch(dispatcherProvider.io) {
            val result = getCakesUseCase.getCakes()

            when (result) {
                is Success -> onSuccess(result)
                is Failure -> onError(result)
            }.exhaustive
        }
    }

    private fun onSuccess(result: Success) =
        cakesResource.postValue(Resource(Resource.Status.SUCCESS, result.cakes))

    private fun onError(result: Failure) =
        cakesResource.postValue(Resource(Resource.Status.ERROR, null, result.exception.message))
}
