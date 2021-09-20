package com.ftresearch.cakes.ui.cakes

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
import com.ftresearch.cakes.ui.TestData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import it.cosenonjaviste.daggermock.DaggerMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CakesFragmentTest {

    @get:Rule
    val rule = DaggerMock.rule<RootComponent>(ViewModelModule()) {
        customizeBuilder<RootComponent.Builder> { it.application(ApplicationProvider.getApplicationContext()) }
        set { it.inject(ApplicationProvider.getApplicationContext()) }
    }

    private val viewState = CakesViewState.Success(
        cakes = TestData.cakes
    )

    private val cakesLiveData = MutableLiveData<CakesViewState>(viewState)

    private val viewModelMock = mock<CakesViewModel> {
        on { cakes } doReturn (cakesLiveData)
    }

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakesFragment>(themeResId = R.style.AppTheme)

        fragmentScenario.onFragment {
            onView(
                withId(R.id.recyclerView)
            ).check(
                matches((hasItemCount(TestData.cakes.size)))
            )
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling
}
