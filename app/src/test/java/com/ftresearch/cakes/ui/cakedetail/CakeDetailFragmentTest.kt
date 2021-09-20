package com.ftresearch.cakes.ui.cakedetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ftresearch.cakes.R
import com.ftresearch.cakes.ui.TestData
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CakeDetailFragmentTest {

    @Test
    fun `should initialise fragment when launched`() {
        val fragmentScenario = launchFragmentInContainer<CakeDetailFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf(
                "title" to TestData.cake1.title,
                "cake" to TestData.cake1
            )
        )

        fragmentScenario.onFragment {
            onView(
                withId(R.id.cakeDetailDescription)
            ).check(
                matches(withText(TestData.cake1.desc))
            )

            onView(
                withId(R.id.cakeDetailDetail)
            ).check(
                matches(withText(TestData.cake1.detail))
            )
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling
}
