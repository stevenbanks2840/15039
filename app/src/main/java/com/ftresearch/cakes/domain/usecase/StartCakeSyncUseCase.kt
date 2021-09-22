package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.sync.CakeSyncRepository
import javax.inject.Inject

class StartCakeSyncUseCase @Inject constructor(private val cakeSyncRepository: CakeSyncRepository) {

    operator fun invoke() = cakeSyncRepository.startSync()
}
