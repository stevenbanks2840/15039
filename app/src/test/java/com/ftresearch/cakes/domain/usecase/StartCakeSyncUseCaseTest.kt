package com.ftresearch.cakes.domain.usecase

import com.ftresearch.cakes.sync.CakeSyncRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class StartCakeSyncUseCaseTest {

    private val cakeSyncRepository = mock<CakeSyncRepository>()

    private lateinit var sut: StartCakeSyncUseCase

    @Before
    fun setUp() {
        sut = StartCakeSyncUseCase(cakeSyncRepository)
    }

    @Test
    fun `should start cake sync`() = runBlocking {
        sut()

        verify(cakeSyncRepository).startSync()
    }
}
