package com.jeft.composelearn.workManager

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.jeft.composelearn.BuildConfig
import com.jeft.composelearn.ComposeApp
import com.jeft.composelearn.R
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


class NotifyWork(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    companion object {
        const val DELAY = 5000
        const val repeatTimes = 6
        const val PUSH_NEW = "push news"
        const val PUSH_NEW_ID = 1001
    }

    override suspend fun doWork(): Result {
        var currentTimes = 0
        while (currentTimes < repeatTimes) {
            Log.d(WorkTest.TAG, "doWork is running")
            delay(5000)
            startNotify(currentTimes.toString())
            currentTimes++
        }
        return Result.success()
    }

    private fun startNotify(num: String) {
        val channelCompat =
            NotificationChannelCompat.Builder(PUSH_NEW, NotificationCompat.PRIORITY_MAX)
                .setName("push new").build()
        val remoteViews = RemoteViews(ComposeApp.app.packageName, R.layout.notify_layout)
        remoteViews.setTextViewText(R.id.text_num, num)
        NotificationManagerCompat.from(ComposeApp.app).apply {
            createNotificationChannel(channelCompat)
            val notify = NotificationCompat.Builder(ComposeApp.app, PUSH_NEW)
                .setPriority(NotificationCompat.PRIORITY_MAX).setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(remoteViews)
//                .setFullScreenIntent(/*PendingIntent.getActivity()*/,true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC).build()
            notify(PUSH_NEW_ID, notify)
        }

    }

}

class WorkTest {
    companion object {
        const val TAG = "Notify_Work"
        const val NOTIFY_WORK_NAME = "notify_work_name"
        fun tryStartWork() {
            val workManager = WorkManager.getInstance(ComposeApp.app)
            val oneTimeWorkRequest =
                OneTimeWorkRequestBuilder<NotifyWork>().setInitialDelay(5, TimeUnit.SECONDS).build()
            workManager.enqueueUniqueWork(
                NOTIFY_WORK_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest
            )
        }
    }
}