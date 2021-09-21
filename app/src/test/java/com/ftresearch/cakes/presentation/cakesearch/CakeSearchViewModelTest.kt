package com.ftresearch.cakes.presentation.cakesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ftresearch.MainCoroutineScopeRule
import com.ftresearch.TestDispatcherProvider
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.domain.usecase.SearchCakesUseCase
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CakeSearchViewModelTest {

    @get:Rule
    val coroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchCakesUseCase = mock<SearchCakesUseCase>()

    private val cakesObserver = mock<Observer<List<Cake>>>()
    private val cakesArgumentCaptor = argumentCaptor<List<Cake>>()

    private lateinit var sut: CakeSearchViewModel

    @Before
    fun setUp() {
        whenever(searchCakesUseCase(EMPTY_SEARCH)).thenReturn(CAKES)

        sut = CakeSearchViewModel(
            searchCakesUseCase = searchCakesUseCase,
            dispatcherProvider = TestDispatcherProvider(coroutineScopeRule.dispatcher)
        )

        sut.searchResults.observe(lifecycleOwner, cakesObserver)
    }

    @Test
    fun `view model should return cakes when initialized`() = coroutineScopeRule.runBlockingTest {
        verify(cakesObserver, times(1)).onChanged(cakesArgumentCaptor.capture())

        assertEquals(CAKES, cakesArgumentCaptor.firstValue)
    }

    @Test
    fun `search should return cakes`() = coroutineScopeRule.runBlockingTest {
        whenever(searchCakesUseCase(TEXT_SEARCH)).thenReturn(SEARCH_RESULTS)

        sut.search(TEXT_SEARCH)

        verify(cakesObserver, times(2)).onChanged(cakesArgumentCaptor.capture())

        assertEquals(CAKES, cakesArgumentCaptor.firstValue)
        assertEquals(SEARCH_RESULTS, cakesArgumentCaptor.secondValue)
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
        val SEARCH_RESULTS = TestData.cakes

        const val EMPTY_SEARCH = ""
        const val TEXT_SEARCH = "text"
    }
}
