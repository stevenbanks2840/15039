package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.domain.repository.CakeRepository
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchCakesUseCaseTest {

    private val cakeRepositoryMock = mock<CakeRepository>()

    private lateinit var sut: SearchCakesUseCase

    @Before
    fun setUp() {
        sut = SearchCakesUseCase(cakeRepositoryMock)
    }

    @Test
    fun `should return cakes`() = runBlocking {
        whenever(cakeRepositoryMock.searchCakes("Any")).doReturn(CAKES)

        val result = sut("Any")

        assertEquals(CAKES, result)
    }

    private companion object {

        val CAKES = TestData.cakes
    }
}
