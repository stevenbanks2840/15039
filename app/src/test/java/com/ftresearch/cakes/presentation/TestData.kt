package com.ftresearch.cakes.presentation

import com.ftresearch.cakes.database.entity.CakeEntity
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.network.model.CakeDTO

object TestData {

    private const val TITLE = "title"
    private const val DESCRIPTION = "description"
    private const val DETAIL = "detail"
    private const val IMAGE = "image"

    val cakeDTO1 = createCakeDTO(1)
    val cakeDTO2 = createCakeDTO(2)
    val cakeDTO3 = createCakeDTO(3)
    val cakeDTO4 = createCakeDTO(3)

    val cakeEntity1 = createCakeEntity(1)
    val cakeEntity2 = createCakeEntity(2)
    val cakeEntity3 = createCakeEntity(3)
    val cakeEntity4 = createCakeEntity(3)

    val cake1 = createCake(1)
    val cake2 = createCake(2)
    val cake3 = createCake(3)
    val cake4 = createCake(3)

    val cakeDTOs = listOf(cakeDTO1, cakeDTO2, cakeDTO3, cakeDTO4)
    val cakeEntities = listOf(cakeEntity1, cakeEntity2, cakeEntity3, cakeEntity4)
    val cakes = listOf(cake1, cake2, cake3, cake4)

    private fun createCakeDTO(index: Int) = CakeDTO(
        title = TITLE + index,
        desc = DESCRIPTION + index,
        detail = DETAIL + index,
        image = IMAGE + index
    )

    private fun createCakeEntity(index: Int) = CakeEntity(
        title = TITLE + index,
        desc = DESCRIPTION + index,
        detail = DETAIL + index,
        image = IMAGE + index
    )

    private fun createCake(index: Int) = Cake(
        title = TITLE + index,
        desc = DESCRIPTION + index,
        detail = DETAIL + index,
        image = IMAGE + index
    )
}
