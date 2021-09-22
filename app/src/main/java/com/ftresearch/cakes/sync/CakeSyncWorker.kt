package com.ftresearch.cakes.sync

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.ftresearch.cakes.R
import javax.inject.Inject

class CakeSyncWorker @Inject constructor(
    context: Context,
    parameters: WorkerParameters,
    private val cakeSynchronizer: CakeSynchronizer
) : CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        return try {
            setForeground(createForegroundInfo())

            cakeSynchronizer.sync()

            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val title = applicationContext.getString(R.string.sync_work_title)
        val text = applicationContext.getText(R.string.sync_work_notification_text)
        val cancel = applicationContext.getString(R.string.sync_work_cancel)

        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, SYNC_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_work_notification)
            .setOngoing(true)
            .setSilent(true)
            // Add the cancel action to the notification which can be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(SYNC_NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = applicationContext.getString(R.string.sync_work_channel_name)
        val description = applicationContext.getString(R.string.sync_work_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(SYNC_NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description
        channel.importance = NotificationManager.IMPORTANCE_LOW
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private companion object {

        const val SYNC_NOTIFICATION_ID = 147
        const val SYNC_NOTIFICATION_CHANNEL_ID = "Cakes"
    }
}
