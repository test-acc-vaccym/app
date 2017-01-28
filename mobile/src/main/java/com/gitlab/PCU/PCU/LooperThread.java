package com.gitlab.PCU.PCU;

@SuppressWarnings("WeakerAccess")
class LooperThread extends Thread {

    private boolean stop = false;

    public void run() {
        while (!stop) {
            loop();
        }
    }

    public void stopLoop() {
        stop = true;
    }

    private void loop() {
        System.out.println("Hey!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }
}
