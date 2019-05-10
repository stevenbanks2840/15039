package com.ftresearch.cakes.services.cake

import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.rest.cake.CakeInterface
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CakeServiceTest {

    private val cake1Mock = mock<Cake> {
        on { title } doReturn ("Cheese cake")
    }
    private val cake2Mock = mock<Cake> {
        on { title } doReturn ("Banana cake")
    }
    private val cake3Mock = mock<Cake> {
        on { title } doReturn ("Cheese cake")
    }
    private val cake4Mock = mock<Cake> {
        on { title } doReturn ("Apple cake")
    }
    private val successfulResponseMock = mock<Response<List<Cake>>> {
        on { body() } doReturn (listOf(cake1Mock))
    }
    private val cakeInterfaceMock = mock<CakeInterface>()
    private val successMock = mock<(List<Cake>) -> Unit>()
    private val errorMock = mock<(String) -> Unit>()

    private val successArgumentCaptor = argumentCaptor<List<Cake>>()
    private val errorArgumentCaptor = argumentCaptor<String>()

    private lateinit var cakeService: CakeService

    @Before
    fun setUp() {
        cakeService = CakeService(cakeInterfaceMock)
    }

    @Test
    fun `getCakes should remove duplicate cakes by title`() = runBlocking {
        whenever(cakeInterfaceMock.getCakesAsync()).doReturn(async { successfulResponseMock })
        whenever(successfulResponseMock.body()).doReturn(listOf(cake1Mock, cake2Mock, cake3Mock, cake4Mock))

        cakeService.getCakes(successMock, errorMock)

        verify(successMock).invoke(successArgumentCaptor.capture())
        val cakes = successArgumentCaptor.firstValue

        assertEquals(3, cakes.size)
        assertTrue(cakes.contains(cake1Mock))
        assertTrue(cakes.contains(cake2Mock))
        assertTrue(cakes.contains(cake4Mock))
    }

    @Test
    fun `getCakes should order cakes by title ascending`() = runBlocking {
        whenever(cakeInterfaceMock.getCakesAsync()).doReturn(async { successfulResponseMock })
        whenever(successfulResponseMock.body()).doReturn(listOf(cake2Mock, cake3Mock, cake4Mock))

        cakeService.getCakes(successMock, errorMock)

        verify(successMock).invoke(successArgumentCaptor.capture())
        val cakes = successArgumentCaptor.firstValue

        assertEquals(3, cakes.size)
        assertEquals(cake4Mock, cakes[0])
        assertEquals(cake2Mock, cakes[1])
        assertEquals(cake3Mock, cakes[2])
    }

    @Test
    fun `getCakes should return failure when cakes are null`() = runBlocking {
        whenever(successfulResponseMock.body()).doReturn(null)
        whenever(cakeInterfaceMock.getCakesAsync()).doReturn(async { successfulResponseMock })

        cakeService.getCakes(successMock, errorMock)

        verify(errorMock).invoke(errorArgumentCaptor.capture())
        val error = errorArgumentCaptor.firstValue

        assertEquals("Failed to retrieve cakes", error)
    }

    @Test
    fun `getCakes should return failure when exception occurs`() = runBlocking {
        whenever(cakeInterfaceMock.getCakesAsync()).doReturn(async { successfulResponseMock })
        whenever(successfulResponseMock.body()).doThrow(NullPointerException())

        cakeService.getCakes(successMock, errorMock)

        verify(errorMock).invoke(errorArgumentCaptor.capture())
        val error = errorArgumentCaptor.firstValue

        assertEquals("Failed to retrieve cakes", error)
    }
}
