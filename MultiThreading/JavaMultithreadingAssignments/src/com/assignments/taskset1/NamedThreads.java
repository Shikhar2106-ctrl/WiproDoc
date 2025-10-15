package com.assignments.taskset1;

class SimpleThread extends Thread {
    public SimpleThread(String name) {
        // Set the thread's name using the superclass constructor
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " has started.");
    }
}

public class NamedThreads {
    public static void main(String[] args) {
        // Create two threads and assign names
        SimpleThread scooby = new SimpleThread("Scooby");
        SimpleThread shaggy = new SimpleThread("Shaggy");

        // Display their names from the main thread
        System.out.println("Created thread with name: " + scooby.getName());
        System.out.println("Created thread with name: " + shaggy.getName());

        // Start the threads to run their task
        scooby.start();
        shaggy.start();
    }