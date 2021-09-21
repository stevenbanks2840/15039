package com.ftresearch.cakes.presentation.cakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ftresearch.MainCoroutineScopeRule
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.domain.usecase.GetCakeSyncStateUseCase
import com.ftresearch.cakes.domain.usecase.GetCakesUseCase
import com.ftresearch.cakes.domain.usecase.StartCakeSyncUseCase
import com.ftresearch.cakes.presentation.TestData
import com.ftresearch.cakes.sync.CakeSyncState
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CakesViewModelTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getCakesUseCase = mock<GetCakesUseCase>()
    private val getCakeSyncStateUseCase = mock<GetCakeSyncStateUseCase>()
    private val startCakeSyncUseCase = mock<StartCakeSyncUseCase>()

    private val cakesObserver = mock<Observer<List<Cake>>>()
    private val cakesArgumentCaptor = argumentCaptor<List<Cake>>()

    private val cakeSyncStateObserver = mock<Observer<CakeSyncState>>()
    private val cakeSyncStateArgumentCaptor = argumentCaptor<CakeSyncState>()

    private lateinit var sut: CakesViewModel

    @Before
    fun setUp() {
        whenever(getCakesUseCase()).thenReturn(CAKES_FLOW)
        whenever(getCakeSyncStateUseCase()).thenReturn(CAKE_SYNC_FLOW)

        sut = CakesViewModel(
            getCakesUseCase = getCakesUseCase,
            getCakeSyncStateUseCase = getCakeSyncStateUseCase,
            startCakeSyncUseCase = startCakeSyncUseCase
        )

        sut.cakes.observe(lifecycleOwner, cakesObserver)
        sut.cakeSyncState.observe(lifecycleOwner, cakeSyncStateObserver)
    }

    @Test
    fun `should return cakes`() = coroutineScopeRule.runBlockingTest {
        verify(cakesObserver, times(1)).onChanged(cakesArgumentCaptor.capture())

        assertEquals(CAKES, cakesArgumentCaptor.firstValue)
    }

    @Test
    fun `should return cake sync state`() = coroutineScopeRule.runBlockingTest {
        verify(cakeSyncStateObserver, times(1)).onChanged(cakeSyncStateArgumentCaptor.capture())

        assertEquals(CAKE_SYNC_STATE, cakeSyncStateArgumentCaptor.firstValue)
    }

    private val lifecycleOwner by lazy {
        mock<LifecycleOwner>().also {
            val lifecycle = LifecycleRegistry(it)
            whenever(it.lifecycle).doReturn(lifecycle)
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    private companion object {

        val CAKES = TestData.cakes
        val CAKES_FLOW = flowOf(CAKES)

        val CAKE_SYNC_STATE = CakeSyncState.Complete
        val CAKE_SYNC_FLOW = MutableSharedFlow<CakeSyncState>(
            replay = 1,
            extraBufferCapacity = 1
        ).also {
            it.tryEmit(CAKE_SYNC_STATE)
        }
    }
}
