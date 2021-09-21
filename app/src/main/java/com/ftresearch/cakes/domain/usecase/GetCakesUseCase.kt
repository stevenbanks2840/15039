package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.domain.repository.CakeRepository
import javax.inject.Inject

class GetCakesUseCase @Inject constructor(private val cakeRepository: CakeRepository) {

    operator fun invoke() = cakeRepository.getCakes()
}
