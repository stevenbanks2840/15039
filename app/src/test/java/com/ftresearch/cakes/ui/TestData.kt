package com.ftresearch.cakes.ui

import com.ftresearch.cakes.rest.cake.Cake

object TestData {

    private const val TITLE = "title"
    private const val DESCRIPTION = "description"
    private const val DETAIL = "detail"
    private val IMAGE = "image"

    val cake1 = createCake(1)
    val cake2 = createCake(2)
    val cake3 = createCake(3)

    val cakes = listOf(TestData.cake1, TestData.cake2, TestData.cake3)

    private fun createCake(index: Int) = Cake(
        title = TITLE + index,
        desc = DESCRIPTION + index,
        detail = DETAIL + index,
        image = IMAGE + index
    )
}
