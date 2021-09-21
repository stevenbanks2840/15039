package com.ftresearch.cakes.domain.repository

import com.ftresearch.cakes.database.dao.CakeDao
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CakesRepositoryTest {

    private val cakeDaoMock = mock<CakeDao>()

    private lateinit var sut: CakeRepository

    @Before
    fun setUp() {
        sut = CakeRepository(cakeDaoMock)
    }

    @Test
    fun `getCakes should return cake entities`() = runBlocking {
        whenever(cakeDaoMock.getCakes()).doReturn(flowOf(TestData.cakeEntities))

        val result = sut.getCakes().first()

        assertEquals(TestData.cakes, result)
    }

    @Test
    fun `searchCakes should return cake entities`() = runBlocking {
        whenever(cakeDaoMock.searchCakesByTitle("Any")).doReturn(TestData.cakeEntities)

        val result = sut.searchCakes("Any")

        assertEquals(TestData.cakes, result)
    }

    @Test
    fun `insertCakes should insert cake entities`() = runBlocking {
        sut.insertCakes(TestData.cakes)

        verify(cakeDaoMock).deleteAllAndInsertCakes(TestData.cakeEntities)
    }
}
