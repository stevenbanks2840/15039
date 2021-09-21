package com.ftresearch.cakes.ui.cakes

import com.ftresearch.cakes.rest.cake.CakeDTO
import com.ftresearch.cakes.rest.cake.CakeService
import com.ftresearch.cakes.usecase.GetCakesUseCase
import com.ftresearch.cakes.usecase.GetCakesUseCase.GetCakesResult
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetCakesUseCaseTest {

    private val cake1Mock = mock<CakeDTO> {
        on { title } doReturn ("Cheese cake")
    }
    private val cake2Mock = mock<CakeDTO> {
        on { title } doReturn ("Banana cake")
    }
    private val cake3Mock = mock<CakeDTO> {
        on { title } doReturn ("Cheese cake")
    }
    private val cake4Mock = mock<CakeDTO> {
        on { title } doReturn ("Apple cake")
    }

    private val cakeServiceMock = mock<CakeService>()

    private lateinit var sut: GetCakesUseCase

    @Before
    fun setUp() {
        sut = GetCakesUseCase(cakeServiceMock)
    }

    @Test
    fun `getCakes should remove duplicate cakes by title`() = runBlocking {
        val response = listOf(
            cake1Mock,
            cake2Mock,
            cake3Mock,
            cake4Mock
        )

        whenever(cakeServiceMock.getCakes()).doReturn(response)

        val result = sut.getCakes() as GetCakesResult.Success

        assertEquals(3, result.cakes.size)
        assertTrue(result.cakes.contains(cake1Mock))
        assertTrue(result.cakes.contains(cake2Mock))
        assertTrue(result.cakes.contains(cake4Mock))
    }

    @Test
    fun `getCakes should order cakes by title ascending`() = runBlocking {
        val response = listOf(
            cake2Mock,
            cake3Mock,
            cake4Mock
        )

        whenever(cakeServiceMock.getCakes()).doReturn(response)

        val result = sut.getCakes() as GetCakesResult.Success

        assertEquals(3, result.cakes.size)
        assertEquals(cake4Mock, result.cakes[0])
        assertEquals(cake2Mock, result.cakes[1])
        assertEquals(cake3Mock, result.cakes[2])
    }

    @Test
    fun `getCakes should return failure when exception occurs`() = runBlocking {
        val exception = RuntimeException()

        whenever(cakeServiceMock.getCakes()).thenThrow(exception)

        val result = sut.getCakes() as GetCakesResult.Error

        assertEquals(exception, result.exception)
    }
}
