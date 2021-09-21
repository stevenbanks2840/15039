package com.ftresearch.cakes.presentation.cakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.domain.usecase.GetCakeSyncStateUseCase
import com.ftresearch.cakes.domain.usecase.GetCakesUseCase
import com.ftresearch.cakes.domain.usecase.StartCakeSyncUseCase
import com.ftresearch.cakes.sync.CakeSyncState
import javax.inject.Inject

class CakesViewModel @Inject constructor(
    private val getCakesUseCase: GetCakesUseCase,
    private val getCakeSyncStateUseCase: GetCakeSyncStateUseCase,
    private val startCakeSyncUseCase: StartCakeSyncUseCase,
) : ViewModel() {

    val cakes: LiveData<List<Cake>> get() = getCakesUseCase().asLiveData()

    val cakeSyncState: LiveData<CakeSyncState>
        get() = getCakeSyncStateUseCase().asLiveData(
            context = viewModelScope.coroutineContext
        )

    fun refresh() = startCakeSyncUseCase()
}
