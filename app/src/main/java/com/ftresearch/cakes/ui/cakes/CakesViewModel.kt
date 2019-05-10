package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftresearch.cakes.DispatcherProvider
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.services.cake.CakeService
import com.ftresearch.cakes.ui.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CakesViewModel @Inject constructor(
        private val cakeService: CakeService,
        dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val scope = CoroutineScope(dispatcherProvider.io)

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

        scope.launch {
            cakeService.getCakes(::onSuccess, ::onError)
        }
    }

    private fun onSuccess(cakes: List<Cake>) = cakesResource.postValue(Resource(Resource.Status.SUCCESS, cakes))

    private fun onError(message: String) = cakesResource.postValue(Resource(Resource.Status.ERROR, null, message))
}
