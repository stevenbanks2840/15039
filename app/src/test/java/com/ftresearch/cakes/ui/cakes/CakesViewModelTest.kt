package com.ftresearch.cakes.ui.cakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ftresearch.cakes.TestDispatcherProvider
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.services.cake.CakeService
import com.ftresearch.cakes.ui.Resource
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CakesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val cakeServiceMock = mock<CakeService>()
    private val cakeMock = mock<Cake>()
    private val cakesObserver = mock<Observer<Resource<List<Cake>>>>()
    private val cakesArgumentCaptor = argumentCaptor<Resource<List<Cake>>>()

    private lateinit var viewModel: CakesViewModel

    @Before
    fun setUp() {
        viewModel = CakesViewModel(cakeServiceMock, TestDispatcherProvider())
    }

    @Test
    fun `should return cakes when getCakes is successful`() = runBlocking {
        whenever(cakeServiceMock.getCakes(any(), any())).doAnswer {
            it.getArgument<(List<Cake>) -> Unit>(0).invoke(listOf(cakeMock))
        }

        viewModel.cakes.observe(lifecycleOwner, cakesObserver)
        viewModel.init()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        val loadingResource = cakesArgumentCaptor.firstValue
        assertEquals(Resource.Status.LOADING, loadingResource.status)

        // Success
        val successResource = cakesArgumentCaptor.secondValue
        assertEquals(Resource.Status.SUCCESS, successResource.status)
        assertEquals(listOf(cakeMock), successResource.data)
    }

    @Test
    fun `should return error when getCakes fails`() = runBlocking {
        whenever(cakeServiceMock.getCakes(any(), any())).doAnswer {
            it.getArgument<(String) -> Unit>(1).invoke(ERROR)
        }

        viewModel.cakes.observe(lifecycleOwner, cakesObserver)
        viewModel.init()

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        // Loading
        val loadingResource = cakesArgumentCaptor.firstValue
        assertEquals(Resource.Status.LOADING, loadingResource.status)

        // Error
        val errorResource = cakesArgumentCaptor.secondValue
        assertEquals(Resource.Status.ERROR, errorResource.status)
        assertEquals(ERROR, errorResource.message)
    }

    private val lifecycleOwner = mock<LifecycleOwner>().also {
        val lifecycle = LifecycleRegistry(it)
        whenever(it.lifecycle).doReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    companion object {

        private const val ERROR = "Failed to load cakes"
    }
}
