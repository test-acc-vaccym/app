package com.github.PCU.android

import android.content.Context
import java.io.IOException
import java.net.ServerSocket
import java.util.logging.Level
import java.util.logging.Logger

internal class LooperThread(private val context: Context) : Thread(), Runnable {
    private var serverSocket: ServerSocket? = null
    private val logger = Logger.getGlobal()

    override fun run() {
        loop()
    }

    private fun loop() {
        try {
            serverSocket = ServerSocket(SERVERPORT)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        while (!Thread.currentThread().isInterrupted) {
            try {
                val socket = serverSocket!!.accept()

                logger.log(Level.INFO, "gotcha!")

                val commThread = CommThread(context, socket)
                commThread.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    companion object {

        private val SERVERPORT = 25566
    }
}
