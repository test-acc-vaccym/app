package com.gitlab.PCU.PCU.helper;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by tim on 01.03.17.
 */

public final class StaticMethods {
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
