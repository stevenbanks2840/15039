package com.ftresearch.cakes.ui.cakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ftresearch.MainCoroutineScopeRule
import com.ftresearch.TestDispatcherProvider
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase.GetCakesResult
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class CakesViewModelTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val cake1Mock = mock<Cake> {
        on { title } doReturn ("Cheese cake")
    }
    private val cake2Mock = mock<Cake> {
        on { title } doReturn ("Banana cake")
    }

    private val cakesMock = listOf(cake1Mock, cake2Mock)
    private val getCakesException = IOException()

    private val getCakesUseCaseMock = mock<GetCakesUseCase>()
    private val getCakeSuccessMock = GetCakesResult.Success(cakesMock)
    private val getCakeFailureMock = GetCakesResult.Error(getCakesException)

    private val cakesObserver = mock<Observer<CakesViewState>>()
    private val cakesArgumentCaptor = argumentCaptor<CakesViewState>()

    private lateinit var sut: CakesViewModel

    @Test
    fun `should return cakes when getCakes is successful`() = coroutineScopeRule.runBlockingTest {
        whenever(getCakesUseCaseMock.getCakes()).thenReturn(getCakeSuccessMock)

        createSut()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        assertTrue(cakesArgumentCaptor.firstValue is CakesViewState.Loading)

        // Success
        assertTrue(cakesArgumentCaptor.secondValue is CakesViewState.Success)
    }

    @Test
    fun `should return error when getCakes fails`() = coroutineScopeRule.runBlockingTest {
        whenever(getCakesUseCaseMock.getCakes()).thenReturn(getCakeFailureMock)

        createSut()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        assertTrue(cakesArgumentCaptor.firstValue is CakesViewState.Loading)

        // Error
        assertTrue(cakesArgumentCaptor.secondValue is CakesViewState.Error)
    }

    private val lifecycleOwner by lazy {
        mock<LifecycleOwner>().also {
            val lifecycle = LifecycleRegistry(it)
            whenever(it.lifecycle).doReturn(lifecycle)
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    private fun createSut() {
        coroutineScopeRule.pauseDispatcher()

        sut = CakesViewModel(
            getCakesUseCaseMock,
            TestDispatcherProvider(coroutineScopeRule.dispatcher)
        )

        sut.cakes.observe(lifecycleOwner, cakesObserver)

        coroutineScopeRule.resumeDispatcher()
    }
}
