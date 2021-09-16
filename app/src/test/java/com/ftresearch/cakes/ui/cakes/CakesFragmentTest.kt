package com.ftresearch.cakes.ui.cakes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.test.core.app.ApplicationProvider
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // TODO: Migrate to AndroidX test
class CakesFragmentTest {

    @get:Rule
    val rule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext()) }
    }

    private val cakesMock = mock<LiveData<CakesViewState>>()

    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesMock)
    }

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakesFragment>()

        fragmentScenario.onFragment { fragment ->
            viewModelMock.cakes.observe(any(), any())

            assertNotNull(fragment)
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling
}
