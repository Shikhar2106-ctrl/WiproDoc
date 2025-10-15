package com.assignments.taskset2;

class DelayedNumberPrinter implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread started. Will print numbers 1 to 10.");
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            if (i == 5) {
                System.out.println("Pausing for 5 seconds after printing 5...");
                try {
                    // Use Thread.sleep() to pause execution
                    Thread.sleep(5000); // 5000 milliseconds = 5 seconds
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted!");
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Thread finished.");
    }
}

public class NumberPrinterWithDelay {
    public static void main(String[] args) {
        DelayedNumberPrinter task = new DelayedNumberPrinter();
        Thread printerThread = new Thread(task);
        printerThread.start();
    }
}