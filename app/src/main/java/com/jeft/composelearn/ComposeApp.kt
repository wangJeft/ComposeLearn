package com.jeft.composelearn

import android.app.ActivityManager
import android.app.Application
import android.os.Process
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeApp : Application() {
    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        /*val currentProcessName: String = getCurrentProcessName()
        val isMainProcess = packageName == currentProcessName
        Log.e("Notify_Work", "isMainProcess, $isMainProcess")*/
    }

    private fun getCurrentProcessName(): String {
        var processName = ""
        try {
            val pid = Process.myPid()
            val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            for (process in manager.runningAppProcesses) {
                if (process.pid == pid) {
                    processName = process.processName
                }
            }
        } catch (e: Exception) {
        }
        return processName
    }
}