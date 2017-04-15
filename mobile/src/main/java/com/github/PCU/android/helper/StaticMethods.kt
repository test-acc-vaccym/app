package com.github.PCU.android.helper

import android.app.ActivityManager
import android.content.Context

/**
 * Created by tim on 01.03.17.
 */

object StaticMethods {
    fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
    }
}
