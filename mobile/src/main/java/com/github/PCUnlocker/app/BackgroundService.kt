package com.github.PCUnlocker.app

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BackgroundService : Service() {

    private var looperThread: LooperThread? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        looperThread = LooperThread(applicationContext)
        looperThread!!.start()
        return Service.START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        looperThread!!.interrupt()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
