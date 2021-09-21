package com.ftresearch.cakes.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class CakeWorkerFactory @Inject constructor(
    private val cakeSynchronizer: CakeSynchronizer
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return when (workerClassName) {
            CakeSyncWorker::class.java.name -> CakeSyncWorker(
                context = appContext,
                parameters = workerParameters,
                cakeSynchronizer = cakeSynchronizer
            )

            else -> null
        }
    }
}
