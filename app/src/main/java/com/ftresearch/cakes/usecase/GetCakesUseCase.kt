package com.ftresearch.cakes.usecase

import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.repository.CakeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCakesUseCase @Inject constructor(private val cakeRepository: CakeRepository) {

    fun getCakes(): Flow<List<Cake>> {
        return cakeRepository.getCakes()
    }
}
