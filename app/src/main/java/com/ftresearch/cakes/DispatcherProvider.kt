package com.ftresearch.cakes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {

    val io: CoroutineContext
}

class CakesDispatcherProvider @Inject constructor() : DispatcherProvider {

    override val io: CoroutineContext = Dispatchers.IO
}
