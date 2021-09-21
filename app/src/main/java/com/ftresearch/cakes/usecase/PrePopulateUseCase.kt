package com.ftresearch.cakes.usecase

import com.ftresearch.cakes.extensions.toCake
import com.ftresearch.cakes.repository.CakeRepository
import com.ftresearch.cakes.rest.cake.CakeService
import javax.inject.Inject

class PrePopulateUseCase @Inject constructor(
    private val cakeRepository: CakeRepository,
    private val cakeService: CakeService
) {

    suspend fun prepopulateCakes(): PrePopulateResult {
        return try {
            val cakes = cakeService
                .getCakes()
                .distinctBy { it.title }
                .map { cakeDTO -> cakeDTO.toCake() }

            cakeRepository.insertCakes(cakes)

            PrePopulateResult.Success
        } catch (exception: Exception) {
            PrePopulateResult.Error(exception)
        }
    }

    sealed class PrePopulateResult {
        object Success : PrePopulateResult()
        data class Error(val exception: Exception) : PrePopulateResult()
    }
}
