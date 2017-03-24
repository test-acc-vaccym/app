package com.gitlab.PCU.PCU

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.gitlab.PCU.PCU.helper.StaticMethods.isServiceRunning

/**
 * Created by tim on 27.01.17.
 */

class onBoot : BroadcastReceiver() {

    /**
     * [BroadcastReceiver.onReceive]
     */
    override fun onReceive(context: Context, intent: Intent) {
        //check here for only boot complete event
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            if (!isServiceRunning(context, BackgroundService::class.java)) {
                //start the service
                val service = Intent(context, BackgroundService::class.java)
                context.startService(service)
            }
        }
    }
}
