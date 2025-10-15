package com.assignments.taskset2;

class PriorityWorker implements Runnable {
    @Override
    public void run() {
        // Perform a long-running, CPU-intensive task
        for (long i = 0; i < 1_000_000_000L; i++) {
            // This loop keeps the CPU busy
        }
        System.out.println(Thread.currentThread().getName() + " (Priority " + Thread.currentThread().getPriority() + ") has finished.");
    }
}

public class ThreadPriorityDemo {
    public static void main(String[] args) {
        System.out.println("Starting threads with different priorities...");

        Thread minPriorityThread = new Thread(new PriorityWorker(), "MinPriorityThread");
        Thread normPriorityThread = new Thread(new PriorityWorker(), "NormPriorityThread");
        Thread maxPriorityThread = new Thread(new PriorityWorker(), "MaxPriorityThread");

        // Set thread priorities
        minPriorityThread.setPriority(Thread.MIN_PRIORITY);   // Priority 1
        normPriorityThread.setPriority(Thread.NORM_PRIORITY); // Priority 5
        maxPriorityThread.setPriority(Thread.MAX_PRIORITY);  // Priority 10

        // Start threads
        maxPriorityThread.start();
        normPriorityThread.start();
        minPriorityThread.start();

        System.out.println("\nNote: Higher priority is only a suggestion to the OS scheduler.");
        System.out.println("The thread with MAX_PRIORITY is likely to finish first, but it is not guaranteed.\n");
    }
}