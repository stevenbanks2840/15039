package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.sync.CakeSyncRepository
import com.ftresearch.cakes.sync.CakeSyncState
import com.ftresearch.cakes.usecase.GetCakesUseCase
import javax.inject.Inject

class CakesViewModel @Inject constructor(
    private val getCakesUseCase: GetCakesUseCase,
    private val cakeSyncRepository: CakeSyncRepository
) : ViewModel() {

    val cakes: LiveData<List<Cake>> get() = getCakesUseCase.getCakes().asLiveData()

    val cakeSyncState: LiveData<CakeSyncState>
        get() = cakeSyncRepository.syncState.asLiveData(
            context = viewModelScope.coroutineContext
        )

    fun refresh() = cakeSyncRepository.startSync()
}
