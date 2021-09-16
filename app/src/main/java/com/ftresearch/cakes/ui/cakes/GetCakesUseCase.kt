package com.ftresearch.cakes.ui.cakes

import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.rest.cake.CakeService
import javax.inject.Inject

class GetCakesUseCase @Inject constructor(private val cakeService: CakeService) {

    suspend fun getCakes(): GetCakesResult {
        return try {
            val cakes = cakeService
                .getCakes()
                .distinctBy { it.title }
                .sortedBy { it.title }

            GetCakesResult.Success(cakes)
        } catch (exception: Exception) {
            GetCakesResult.Error(exception)
        }
    }

    sealed class GetCakesResult {
        data class Success(val cakes: List<Cake>) : GetCakesResult()
        data class Error(val exception: Exception) : GetCakesResult()
    }
}
