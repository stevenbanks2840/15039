package com.ftresearch.cakes

import android.util.Log
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper

class TestCakesApplication : CakesApplication() {

    override fun onCreate() {
        super.onCreate()

        setTheme(R.style.AppTheme)

        val config = Configuration.Builder()
            .setWorkerFactory(cakeWorkerFactory)
            .setExecutor(SynchronousExecutor())
            .setMinimumLoggingLevel(Log.DEBUG)
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(this, config)
    }
}
