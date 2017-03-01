package com.gitlab.PCU.PCU;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BackgroundService extends Service {

    private LooperThread looperThread;

    public BackgroundService() {
        looperThread = new LooperThread();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        looperThread.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        looperThread.interrupt();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
