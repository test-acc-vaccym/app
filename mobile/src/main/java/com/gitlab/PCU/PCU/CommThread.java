package com.gitlab.PCU.PCU;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tim on 01.03.17.
 */

class CommThread extends Thread implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private Logger logger = Logger.getGlobal();
    private Context context;

    CommThread(Context context, Socket csock) {
        this.context = context;
        socket = csock;
        try {
            socket.setSoTimeout(10000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        notifyUser(socket.getInetAddress().getHostName());
        while ((!Thread.currentThread().isInterrupted()) && socket.isConnected()) {
            try {
                String read = input.readLine();
                if (read == null) {
                    break;
                }
                logger.log(Level.INFO, read);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.INFO, "closed");
    }

    private void notifyUser(String msg) {
        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_fingerprint_green_24dp)
                        .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_fingerprint_green_24dp))
                        .setContentTitle(context.getText(R.string.unlock_notification_tilte))
                        .setContentText(msg);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, UnlockActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(UnlockActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, notification);
    }
}
