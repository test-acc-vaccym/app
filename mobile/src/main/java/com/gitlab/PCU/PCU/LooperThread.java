package com.gitlab.PCU.PCU;

import android.content.Context;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
class LooperThread extends Thread implements Runnable {

    private static final int SERVERPORT = 25566;
    private ServerSocket serverSocket;
    private Logger logger = Logger.getGlobal();
    private Context context;

    LooperThread(Context context) {
        super();
        this.context = context;
    }

    public void run() {
        loop();
    }

    private void loop() {
        try {
            serverSocket = new ServerSocket(SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();

                logger.log(Level.INFO, "gotcha!");

                CommThread commThread = new CommThread(context, socket);
                commThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
