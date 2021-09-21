package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.domain.repository.CakeRepository
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCakesUseCaseTest {

    private val cakeRepositoryMock = mock<CakeRepository>()

    private lateinit var sut: GetCakesUseCase

    @Before
    fun setUp() {
        sut = GetCakesUseCase(cakeRepositoryMock)
    }

    @Test
    fun `should return cakes`() = runBlocking {
        val cakes = flowOf(TestData.cakes)

        whenever(cakeRepositoryMock.getCakes()).doReturn(cakes)

        val result = sut()

        assertEquals(cakes, result)
    }
}
