package com.ftresearch.cakes

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestDispatcherProvider : DispatcherProvider {

    override val io: CoroutineContext = Dispatchers.Unconfined
}
