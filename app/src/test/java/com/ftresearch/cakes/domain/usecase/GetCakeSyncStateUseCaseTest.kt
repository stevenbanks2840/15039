package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.sync.CakeSyncRepository
import com.ftresearch.cakes.sync.CakeSyncState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCakeSyncStateUseCaseTest {

    private val cakeSyncRepository = mock<CakeSyncRepository>()

    private lateinit var sut: GetCakeSyncStateUseCase

    @Before
    fun setUp() {
        sut = GetCakeSyncStateUseCase(cakeSyncRepository)
    }

    @Test
    fun `should return cake sync state`() = runBlocking {
        whenever(cakeSyncRepository.syncState) doReturn (CAKES_FLOW)

        val result = sut()

        assertEquals(CAKES_FLOW, result)
    }

    private companion object {

        val CAKES_FLOW = mock<SharedFlow<CakeSyncState>>()
    }
}
