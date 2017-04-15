package com.github.PCUnlocker.app

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket
import java.net.SocketException
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Created by tim on 01.03.17.
 */

internal class CommThread(private val context: Context, private val socket: Socket) : Thread(), Runnable {
    private var input: BufferedReader? = null
    private val logger = Logger.getGlobal()

    init {
        try {
            socket.soTimeout = 10000
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        try {
            input = BufferedReader(InputStreamReader(socket.getInputStream()))
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun run() {
        notifyUser(socket.inetAddress.hostName)
        while (!Thread.currentThread().isInterrupted && socket.isConnected) {
            try {
                val read = input!!.readLine() ?: break
                logger.log(Level.INFO, read)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        logger.log(Level.INFO, "closed")
    }

    private fun notifyUser(msg: String) {
        val mBuilder = Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_fingerprint_green_24dp)
                .setLargeIcon(Icon.createWithResource(context, R.drawable.ic_fingerprint_green_24dp))
                .setContentTitle(context.getText(R.string.unlock_notification_tilte))
                .setContentText(msg)
        // Creates an explicit intent for an Activity in your app
        val resultIntent = Intent(context, UnlockActivity::class.java)

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        val stackBuilder = TaskStackBuilder.create(context)
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(UnlockActivity::class.java)
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(resultPendingIntent)
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = mBuilder.build()
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, notification)
    }
}
