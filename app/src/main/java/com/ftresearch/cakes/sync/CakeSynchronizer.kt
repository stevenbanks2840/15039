package com.ftresearch.cakes.sync

import com.ftresearch.cakes.domain.repository.CakeRepository
import com.ftresearch.cakes.extensions.toCake
import com.ftresearch.cakes.network.service.CakeService
import javax.inject.Inject

class CakeSynchronizer @Inject constructor(
    private val cakeSyncRepository: CakeSyncRepository,
    private val cakeRepository: CakeRepository,
    private val cakeService: CakeService
) {
    suspend fun sync() {
        try {
            cakeSyncRepository.updateState(CakeSyncState.InProgress)

            downloadCakes()

            cakeSyncRepository.updateState(CakeSyncState.Complete)
        } catch (exception: Exception) {
            cakeSyncRepository.updateState(CakeSyncState.Error(exception))
            throw exception
        }
    }

    private suspend fun downloadCakes() {
        val cakes = cakeService
            .getCakes()
            .distinctBy { it.title }
            .map { cakeDTO -> cakeDTO.toCake() }

        cakeRepository.insertCakes(cakes)
    }
}
