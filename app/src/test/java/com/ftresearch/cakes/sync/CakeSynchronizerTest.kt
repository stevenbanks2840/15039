package com.ftresearch.cakes.sync

import com.ftresearch.MainCoroutineScopeRule
import com.ftresearch.cakes.domain.repository.CakeRepository
import com.ftresearch.cakes.network.service.CakeService
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.inOrder

@ExperimentalCoroutinesApi
class CakeSynchronizerTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    private val cakeSyncRepositoryMock = mock<CakeSyncRepository>()
    private val cakeRepositoryMock = mock<CakeRepository>()
    private val cakeServiceMock = mock<CakeService>()

    private lateinit var sut: CakeSynchronizer

    @Before
    fun setUp() {
        sut = CakeSynchronizer(
            cakeSyncRepository = cakeSyncRepositoryMock,
            cakeRepository = cakeRepositoryMock,
            cakeService = cakeServiceMock
        )
    }

    @After
    fun cleanUp() {
        try {
            coroutineScopeRule.cleanupTestCoroutines()
        } catch (e: Exception) {
            //Do something here
        }
    }

    @Test
    fun `sync should update sync state, remove duplicates and persist cake entities when successful`() = coroutineScopeRule.runBlockingTest {
        whenever(cakeServiceMock.getCakes()).thenReturn(CAKE_DTO_WITH_DUPLICATES)

        sut.sync()

        val inOrder = inOrder(cakeSyncRepositoryMock, cakeServiceMock, cakeRepositoryMock, cakeSyncRepositoryMock)

        inOrder.verify(cakeSyncRepositoryMock).updateState(CakeSyncState.InProgress)
        inOrder.verify(cakeServiceMock).getCakes()
        inOrder.verify(cakeRepositoryMock).insertCakes(CAKES)
        inOrder.verify(cakeSyncRepositoryMock).updateState(CakeSyncState.Complete)
    }

    @Test
    fun `sync should update sync state when an exception occurs`() = coroutineScopeRule.runBlockingTest {
        val exception = RuntimeException()
        val exceptionHandler = TestCoroutineExceptionHandler()

        whenever(cakeServiceMock.getCakes()).thenThrow(exception)

        launch(exceptionHandler) {
            sut.sync()
        }

        val inOrder = inOrder(cakeSyncRepositoryMock, cakeServiceMock, cakeSyncRepositoryMock)

        inOrder.verify(cakeSyncRepositoryMock).updateState(CakeSyncState.InProgress)
        inOrder.verify(cakeServiceMock).getCakes()
        inOrder.verify(cakeSyncRepositoryMock).updateState(CakeSyncState.Error(exception))

        assertEquals(exception, exceptionHandler.uncaughtExceptions.first())
    }

    private companion object {

        val CAKE_DTO_WITH_DUPLICATES = TestData.cakeDTOs + TestData.cakeDTOs

        val CAKES = TestData.cakes
    }
}
