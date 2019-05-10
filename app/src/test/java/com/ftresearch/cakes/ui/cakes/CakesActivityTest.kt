package com.ftresearch.cakes.ui.cakes

import androidx.lifecycle.LiveData
import androidx.test.core.app.ApplicationProvider
import com.ftresearch.cakes.CakesApplication
import com.ftresearch.cakes.di.RootComponent
import com.ftresearch.cakes.di.ViewModelModule
import com.ftresearch.cakes.rest.cake.Cake
import com.ftresearch.cakes.ui.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // TODO: Migrate to AndroidX test
class CakesActivityTest {

    @get:Rule
    val rule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext<CakesApplication>()) }
    }

    private val cakesMock = mock<LiveData<Resource<List<Cake>>>>()
    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesMock)
    }

    private lateinit var activity: CakesActivity

    @Before
    fun setUp() {
        activity = buildActivity(CakesActivity::class.java).setup().get()
    }

    @Test
    fun `should initialise view model when activity is created`() {
        verify(viewModelMock).init()
    }

    // TODO: Add more UI tests e.g. refresh and error handling
}
