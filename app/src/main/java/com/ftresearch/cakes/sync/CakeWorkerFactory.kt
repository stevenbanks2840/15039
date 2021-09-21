package com.ftresearch.cakes.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ftresearch.cakes.repository.CakeRepository
import com.ftresearch.cakes.rest.cake.CakeService
import javax.inject.Inject

class CakeWorkerFactory @Inject constructor(
    private val cakeSyncRepository: CakeSyncRepository,
    private val cakeRepository: CakeRepository,
    private val cakeService: CakeService
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return when (workerClassName) {
            CakeSyncWorker::class.java.name -> CakeSyncWorker(
                context = appContext,
                parameters = workerParameters,
                cakeSyncRepository = cakeSyncRepository,
                cakeRepository = cakeRepository,
                cakeService = cakeService
            )

            else -> null
        }
    }
}
