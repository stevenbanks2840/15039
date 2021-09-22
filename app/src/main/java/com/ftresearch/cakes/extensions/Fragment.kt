package com.ftresearch.cakes.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.ftresearch.cakes.R

fun Fragment.setupActionBarNavigation(toolbar: Toolbar) {
    toolbar.isVisible = true

    (activity as? AppCompatActivity)?.run {
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }
}
