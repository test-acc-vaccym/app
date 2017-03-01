package com.gitlab.PCU.PCU;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.gitlab.PCU.PCU.helper.StaticMethods.isServiceRunning;

/**
 * Created by tim on 27.01.17.
 */

public class onBoot extends BroadcastReceiver {

    /**
     * {@link BroadcastReceiver#onReceive(Context, Intent)}
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //check here for only boot complete event
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            if (!isServiceRunning(context, BackgroundService.class)) {
                //start the service
                Intent service = new Intent(context, BackgroundService.class);
                context.startService(service);
            }
        }
    }
}
