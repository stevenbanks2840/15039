package com.ftresearch.cakes.ui.cakedetail

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import com.ftresearch.cakes.rest.cake.Cake
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // TODO: Migrate to AndroidX test
class CakeDetailFragmentTest {

    @Test
    fun `should initialise view model when activity is created`() {
        val fragmentScenario = launchFragmentInContainer<CakeDetailFragment>(
            bundleOf(
                "title" to TITLE,
                "cake" to CAKE
            )
        )

        fragmentScenario.onFragment { fragment ->
            assertNotNull(fragment)
        }
    }

    // TODO: Add more UI tests e.g. refresh and error handling

    companion object {

        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private val IMAGE = null

        private val CAKE = Cake(
            title = TITLE,
            desc = DESCRIPTION,
            image = IMAGE
        )
    }
}
