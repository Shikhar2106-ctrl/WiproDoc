package com.assignments.taskset1;

import java.util.Random;

class ColorRunnable implements Runnable {
    private final String[] colors = {"white", "blue", "black", "green", "red", "yellow"};
    private final Random random = new Random();

    @Override
    public void run() {
        while (true) {
            // Generate a random index to pick a color
            int index = random.nextInt(colors.length);
            String color = colors[index];
            System.out.println(color);

            // If the color is "red", stop the thread
            if (color.equals("red")) {
                System.out.println("Found red! Stopping.");
                break;
            }

            try {
                // Pause for a moment to make the output easier to read
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted.");
                break;
            }
        }
    }
}

public class RandomColorGenerator {
    public static void main(String[] args) {
        ColorRunnable colorTask = new ColorRunnable();
        Thread colorThread = new Thread(colorTask);
        colorThread.start();
    }
}