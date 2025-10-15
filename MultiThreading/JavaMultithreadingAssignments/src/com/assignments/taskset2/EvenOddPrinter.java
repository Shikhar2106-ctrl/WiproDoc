package com.assignments.taskset2;

class EvenPrinter implements Runnable {
    @Override
    public void run() {
        System.out.println("--- Even Numbers (1-20) ---");
        for (int i = 1; i <= 20; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }
}

class OddPrinter implements Runnable {
    @Override
    public void run() {
        System.out.println("\n--- Odd Numbers (1-20) ---");
        for (int i = 1; i <= 20; i++) {
            if (i % 2 != 0) {
                System.out.println(i);
            }
        }
    }
}

public class EvenOddPrinter {
    public static void main(String[] args) {
        Thread evenThread = new Thread(new EvenPrinter());
        Thread oddThread = new Thread(new OddPrinter());

        // Start the thread that prints even numbers
        evenThread.start();

        try {
            // The main thread will wait here until evenThread finishes its execution
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Once the evenThread is done, start the oddThread
        oddThread.start();
    }
}