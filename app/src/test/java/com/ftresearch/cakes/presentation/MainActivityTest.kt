package com.ftresearch.cakes.presentation

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.presentation.cakes.CakesViewModel
import com.ftresearch.cakes.sync.CakeSyncState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val daggerMockRule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext()) }
    }

    private val cakesLiveData = MutableLiveData(CAKES)
    private val cakeSyncStateLiveData = MutableLiveData<CakeSyncState>(CakeSyncState.Complete)

    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesLiveData)
        on { cakeSyncState } doReturn (cakeSyncStateLiveData)
    }

    @Test
    fun `should initialise activity`() {
        val scenario = launchActivity<MainActivity>()

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling

    private companion object {

        val CAKES = TestData.cakes
    }
}
