package com.ftresearch.cakes.ui.cakes

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.test.core.app.ApplicationProvider
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import it.cosenonjaviste.daggermock.DaggerMock
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

    private val cakesMock = mock<LiveData<Resource<List<Cake>>>>()
    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesMock)
    }

    @Test
    fun `should initialise view model when activity is created`() {
        launchFragmentInContainer<CakesFragment>()

        verify(viewModelMock).init()
    }

    // TODO: Add more UI tests e.g. refresh and error handling
}
