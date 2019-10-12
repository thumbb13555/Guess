package com.example.guess

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class CacheService() :IntentService("CacheService"){
    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent")
        Thread.sleep(5000)
    }

    private val TAG:String = CacheService::class.java.simpleName


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy ");
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}