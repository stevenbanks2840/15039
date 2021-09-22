package com.ftresearch.cakes.sync

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.ftresearch.cakes.CakesApplication
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CakeSyncRepository @Inject constructor(private val application: CakesApplication) {

    private val _syncState = MutableSharedFlow<CakeSyncState>(
        replay = 1,
        extraBufferCapacity = 1
    )

    val syncState: SharedFlow<CakeSyncState> = _syncState

    fun updateState(syncState: CakeSyncState) {
        _syncState.tryEmit(syncState)
    }

    fun startSync() {
        val request: WorkRequest = OneTimeWorkRequestBuilder<CakeSyncWorker>()
            .build()

        WorkManager
            .getInstance(application)
            .enqueue(request)

    }
}
