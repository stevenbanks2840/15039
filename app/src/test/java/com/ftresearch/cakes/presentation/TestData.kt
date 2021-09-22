package com.ftresearch.cakes.presentation

import com.ftresearch.cakes.extensions.toCake
import com.ftresearch.cakes.extensions.toCakeEntity
import com.ftresearch.cakes.network.model.CakeDTO

object TestData {

    const val searchTerm = "sponge"

    val cakeDTOs = listOf(
        CakeDTO(
            title = "Lemon $searchTerm",
            desc = "Lemon $searchTerm desc",
            detail = "Lemon $searchTerm detail",
            image = "Lemon $searchTerm image"
        ),
        CakeDTO(
            title = "Fruit cake",
            desc = "Fruit cake desc",
            detail = "Fruit cake detail",
            image = "Fruit cake image"
        ),
        CakeDTO(
            title = "Apple $searchTerm",
            desc = "Apple $searchTerm desc",
            detail = "Apple $searchTerm detail",
            image = "Apple $searchTerm image"
        ),
        CakeDTO(
            title = "Banana cake",
            desc = "Banana cake desc",
            detail = "Banana cake detail",
            image = "Banana cake image"
        )
    )

    val cakes = cakeDTOs.map { cakeDTO -> cakeDTO.toCake() }

    val cakeEntities = cakes.map { cakeDTO -> cakeDTO.toCakeEntity() }
}
