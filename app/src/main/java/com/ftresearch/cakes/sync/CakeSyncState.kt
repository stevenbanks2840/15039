package com.ftresearch.cakes.sync

sealed class CakeSyncState {
    object InProgress : CakeSyncState()
    object Complete : CakeSyncState()
    data class Error(val exception: Exception) : CakeSyncState()
}
