package com.jeft.composelearn

import android.app.Application
import android.util.Log
import com.jeft.composelearn.workManager.WorkTest

class ComposeApp : Application() {
    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("Notify_Work", "ComposeApp, onCreate:", )
        app = this
        WorkTest.tryStartWork()
    }
}