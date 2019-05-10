package com.ftresearch.cakes

import android.os.Bundle
import android.util.Log
import com.ftresearch.cakes.services.cake.CakeService
import dagger.android.DaggerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : DaggerActivity() {

    @Inject
    lateinit var cakeService: CakeService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // TODO: Replace with real UI
        GlobalScope.launch {
            cakeService.getCakes(
                {
                    Log.d("Cakes", "cakes=$it")
                },
                {
                    Log.d("Cakes", "$it")
                }
            )
        }
    }
}
