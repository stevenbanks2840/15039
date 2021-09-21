package com.ftresearch.cakes.presentation.cakes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.cakes.R
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.espresso.RecyclerViewMatchers.hasItemCount
import com.ftresearch.cakes.presentation.TestData
import com.ftresearch.cakes.sync.CakeSyncState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLooper

@RunWith(AndroidJUnit4::class)
class CakesFragmentTest {

    @get:Rule
    val daggerMockRule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext()) }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cakesLiveData = MutableLiveData(CAKES)
    private val cakeSyncStateLiveData = MutableLiveData<CakeSyncState>(CakeSyncState.Complete)

    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesLiveData)
        on { cakeSyncState } doReturn (cakeSyncStateLiveData)
    }

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakesFragment>(themeResId = R.style.AppTheme)

        fragmentScenario.onFragment {
            onView(
                withId(R.id.recyclerView)
            ).check(
                matches((hasItemCount(CAKES.size)))
            )
        }
    }

    @Test
    fun `should display progress when cakes are being synced`() {
        val fragmentScenario = launchFragmentInContainer<CakesFragment>(themeResId = R.style.AppTheme)

        fragmentScenario.onFragment {
            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(not(isDisplayed()))
            )

            cakeSyncStateLiveData.postValue(CakeSyncState.InProgress)

            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(isDisplayed())
            )
        }
    }

    @Test
    fun `should hide progress when cakes have synced successfully`() {
        cakeSyncStateLiveData.postValue(CakeSyncState.InProgress)

        val fragmentScenario = launchFragmentInContainer<CakesFragment>(themeResId = R.style.AppTheme)

        fragmentScenario.onFragment {
            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(isDisplayed())
            )

            cakeSyncStateLiveData.postValue(CakeSyncState.Complete)

            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(not(isDisplayed()))
            )
        }
    }

    @Test
    fun `should hide progress when cakes have failed to sync`() {
        cakeSyncStateLiveData.postValue(CakeSyncState.InProgress)

        val fragmentScenario = launchFragmentInContainer<CakesFragment>(themeResId = R.style.AppTheme)

        fragmentScenario.onFragment {
            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(isDisplayed())
            )

            cakeSyncStateLiveData.postValue(CakeSyncState.Error(RuntimeException()))

            waitForContentLoadingProgressBar()

            onView(
                withId(R.id.progress)
            ).check(
                matches(not(isDisplayed()))
            )
        }
    }

    private fun waitForContentLoadingProgressBar() {
        // Ouch - The ContentLoadingProgressBar view has weired behaviour using 2 posts to Handler to show() or hide()
        repeat(2) {
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling

    private companion object {

        val CAKES = TestData.cakes
    }
}
