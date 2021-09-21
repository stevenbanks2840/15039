package com.ftresearch.cakes.presentation.cakesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.cakes.R
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.espresso.RecyclerViewMatchers.hasItemCount
import com.ftresearch.cakes.presentation.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CakeSearchFragmentTest {

    @get:Rule
    val daggerMockRule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext()) }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cakesLiveData = MutableLiveData(CAKES)

    private val viewModelMock = mock<CakeSearchViewModel> {
        on { searchResults } doReturn (cakesLiveData)
    }

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakeSearchFragment>()

        fragmentScenario.onFragment {
            onView(
                withId(R.id.recyclerView)
            ).check(
                matches((hasItemCount(CAKES.size)))
            )
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling

    private companion object {

        val CAKES = TestData.cakes
    }
}
