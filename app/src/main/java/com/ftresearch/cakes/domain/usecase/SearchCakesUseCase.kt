package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.domain.repository.CakeRepository
import javax.inject.Inject

class SearchCakesUseCase @Inject constructor(private val cakeRepository: CakeRepository) {

    operator fun invoke(text: String) = cakeRepository.searchCakes(text)
}
