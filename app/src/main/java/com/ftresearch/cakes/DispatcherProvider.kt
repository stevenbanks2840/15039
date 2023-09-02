package com.ftresearch.cakes

import android.util.Log
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {

    val io: CoroutineContext
}

class CakesDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val io: CoroutineContext = Dispatchers.IO
}

class UnusedDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val io: CoroutineContext = Dispatchers.IO

    init {
        largeMethod()
    }

    private fun largeMethod() {
        val list = listOf("two", "one", "three", "four", "five")
        val sortedListed = list.sorted()
        val reversedList = list.reversed()
        val combinedList = list + sortedListed + reversedList
        combinedList.forEach { item ->
            Log.d("largeMethod", "item=$item")
        }
    }
}