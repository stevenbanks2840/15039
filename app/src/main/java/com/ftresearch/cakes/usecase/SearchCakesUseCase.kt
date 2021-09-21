package com.ftresearch.cakes.usecase

import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.repository.CakeRepository
import javax.inject.Inject

class SearchCakesUseCase @Inject constructor(private val cakeRepository: CakeRepository) {

    fun searchCakes(text: String): List<Cake> {
        return cakeRepository.searchCakes(text)
    }
}
