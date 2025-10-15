package com.assignments.race;

class Racer implements Runnable {
    private final String name;
    private final int totalDistance = 100;

    public Racer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int distance = 1; distance <= totalDistance; distance++) {
            // Check if another racer has already won. If so, stop running.
            if (HareAndTortoiseRace.winner != null) {
                break;
            }

            System.out.println(this.name + " is at " + distance + " meters.");

            // Part C specific logic: The Hare takes a nap.
            if (this.name.equals("Hare") && distance == 60 && HareAndTortoiseRace.isHareSleeping) {
                try {
                    System.out.println(">>>> The Hare is taking a nap... <<<<");
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        // This synchronized block ensures only one thread can declare itself the winner.
        synchronized (HareAndTortoiseRace.class) {
            if (HareAndTortoiseRace.winner == null) {
                HareAndTortoiseRace.winner = this.name;
                System.out.println("\n*********************************");
                System.out.println("   WINNER: " + this.name + "!");
                System.out.println("*********************************\n");
            }
        }
    }
}

public class HareAndTortoiseRace {
    // 'volatile' ensures that changes to this variable are visible across all threads.
    public static volatile String winner = null;
    public static boolean isHareSleeping = false;

    public static void main(String[] args) throws InterruptedException {
        runPartA();
        runPartB();
        runPartC();
    }

    private static void resetRace() {
        winner = null;
        isHareSleeping = false;
    }

    private static void runPartA() throws InterruptedException {
        System.out.println("======== PART A: STARTING RACE (Normal Priority) ========");
        resetRace();

        Thread tortoise = new Thread(new Racer("Tortoise"));
        Thread hare = new Thread(new Racer("Hare"));

        hare.start();
        tortoise.start();

        // Wait for both threads to finish before moving on
        hare.join();
        tortoise.join();
        System.out.println("======== PART A: Race Finished ========\n");
    }
    
    private static void runPartB() throws InterruptedException {
        System.out.println("======== PART B: STARTING RACE (Hare has MAX_PRIORITY) ========");
        resetRace();

        Thread tortoise = new Thread(new Racer("Tortoise"));
        Thread hare = new Thread(new Racer("Hare"));

        // Give Hare a higher priority
        hare.setPriority(Thread.MAX_PRIORITY);
        
        hare.start();
        tortoise.start();

        hare.join();
        tortoise.join();
        System.out.println("======== PART B: Race Finished ========\n");
    }

    private static void runPartC() throws InterruptedException {
        System.out.println("======== PART C: STARTING RACE (Hare sleeps at 60m) ========");
        resetRace();
        isHareSleeping = true; 

        Thread tortoise = new Thread(new Racer("Tortoise"));
        Thread hare = new Thread(new Racer("Hare"));
        
        // Give Hare high priority again to make the nap's effect clear
        hare.setPriority(Thread.MAX_PRIORITY);

        hare.start();
        tortoise.start();

        hare.join();
        tortoise.join();
        System.out.println("======== PART C: Race Finished ========\n");
    }
}  