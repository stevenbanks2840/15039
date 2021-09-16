package com.ftresearch.cakes.ui.cakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ftresearch.cakes.TestDispatcherProvider
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.ftresearch.cakes.ui.cakes.GetCakesUseCase.GetCakesResult
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CakesViewModelTest {

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
    private val getCakeFailureMock = GetCakesResult.Failure(getCakesException)

    private val cakesObserver = mock<Observer<Resource<List<Cake>>>>()
    private val cakesArgumentCaptor = argumentCaptor<Resource<List<Cake>>>()

    private lateinit var sut: CakesViewModel

    @Before
    fun setUp() {
        sut = CakesViewModel(getCakesUseCaseMock, TestDispatcherProvider())
    }

    @Test
    fun `should return cakes when getCakes is successful`() = runBlocking {
        whenever(getCakesUseCaseMock.getCakes()).thenReturn(getCakeSuccessMock)

        sut.cakes.observe(lifecycleOwner, cakesObserver)
        sut.init()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        val loadingResource = cakesArgumentCaptor.firstValue
        assertEquals(Resource.Status.LOADING, loadingResource.status)

        // Success
        val successResource = cakesArgumentCaptor.secondValue
        assertEquals(Resource.Status.SUCCESS, successResource.status)
        assertEquals(cakesMock, successResource.data)
    }

    @Test
    fun `should return error when getCakes fails`() = runBlocking {
        whenever(getCakesUseCaseMock.getCakes()).thenReturn(getCakeFailureMock)

        sut.cakes.observe(lifecycleOwner, cakesObserver)
        sut.init()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        val loadingResource = cakesArgumentCaptor.firstValue
        assertEquals(Resource.Status.LOADING, loadingResource.status)

        // Error
        val errorResource = cakesArgumentCaptor.secondValue
        assertEquals(Resource.Status.ERROR, errorResource.status)
        assertEquals(getCakesException.message, errorResource.message)
    }

    private val lifecycleOwner by lazy {
        mock<LifecycleOwner>().also {
            val lifecycle = LifecycleRegistry(it)
            whenever(it.lifecycle).doReturn(lifecycle)
            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }
}
