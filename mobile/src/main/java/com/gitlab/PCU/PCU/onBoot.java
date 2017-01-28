package com.gitlab.PCU.PCU;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tim on 27.01.17.
 */

public class onBoot extends BroadcastReceiver {

    private static boolean started = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!started) {
            //check here for only boot complete event
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                //start the service
                Intent service = new Intent(context, BackgroundService.class);
                context.startService(service);
            }
            started = true;
        }

    }
}
