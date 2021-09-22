package com.ftresearch.cakes.presentation.cakedetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.cakes.R
import com.ftresearch.cakes.presentation.TestData
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CakeDetailFragmentTest {

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakeDetailFragment>(
            fragmentArgs = CakeDetailFragmentArgs.Builder(CAKE.title, false, CAKE)
                .build()
                .toBundle()
        )

        fragmentScenario.onFragment {
            onView(
                withId(R.id.cakeDetailDescription)
            ).check(
                matches(withText(CAKE.desc))
            )

            onView(
                withId(R.id.cakeDetailDetail)
            ).check(
                matches(withText(CAKE.detail))
            )
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling

    private companion object {

        val CAKE = TestData.cakes[1]
    }
}
