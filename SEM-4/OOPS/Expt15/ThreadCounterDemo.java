// Q1: Three threads each printing their name and count 1 to 4
// Uses Thread.currentThread().getName() and sleep() to show interleaved output

class CounterThread extends Thread {
    public CounterThread(String name) {
        super(name); // sets thread name
    }

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            System.out.println(Thread.currentThread().getName() + "  -->  Count: " + i);
            try {
                // Sleep for a random time between 200-600ms to produce interleaved output
                Thread.sleep(200 + (long)(Math.random() * 400));
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + "  -->  [Done]");
    }
}

public class ThreadCounterDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Counter Demo ===");
        System.out.println("Three threads will print their name and count 1 to 4.");
        System.out.println("Notice the interleaved output due to sleep().\n");

        Thread t1 = new CounterThread("Thread-Alpha");
        Thread t2 = new CounterThread("Thread-Beta ");
        Thread t3 = new CounterThread("Thread-Gamma");

        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish before printing summary
        t1.join();
        t2.join();
        t3.join();

        System.out.println("\nAll three threads have finished.");
    }
}
