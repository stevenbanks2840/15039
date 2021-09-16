package com.ftresearch

import com.ftresearch.cakes.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestDispatcherProvider(testCoroutineDispatcher: TestCoroutineDispatcher) :
    DispatcherProvider {

    override val io: CoroutineContext = testCoroutineDispatcher
}
