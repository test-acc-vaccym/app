package com.gitlab.PCU.PCU;

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

    CommThread(Socket csock) {
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
}
