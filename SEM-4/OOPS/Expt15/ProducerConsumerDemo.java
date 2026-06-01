// Q3: Producer-Consumer problem with shared buffer of size 1
// Producer adds item when buffer is empty; Consumer removes item when buffer is full
// Uses wait() and notify() for coordination

class SharedBuffer {
    private int item;
    private boolean hasItem = false; // buffer state: empty or full

    // Producer calls this — waits if buffer is full, then adds item and notifies consumer
    public synchronized void produce(int value) throws InterruptedException {
        while (hasItem) {
            System.out.println("[Producer] Buffer is FULL. Waiting...");
            wait(); // release lock and wait until consumer notifies
        }
        item    = value;
        hasItem = true;
        System.out.println("[Producer] Produced item: " + item);
        notify(); // wake up the consumer
    }

    // Consumer calls this — waits if buffer is empty, then removes item and notifies producer
    public synchronized int consume() throws InterruptedException {
        while (!hasItem) {
            System.out.println("[Consumer] Buffer is EMPTY. Waiting...");
            wait(); // release lock and wait until producer notifies
        }
        hasItem = false;
        System.out.println("[Consumer] Consumed item: " + item);
        notify(); // wake up the producer
        return item;
    }
}

class Producer extends Thread {
    private SharedBuffer buffer;
    private int totalItems;

    public Producer(SharedBuffer buffer, int totalItems) {
        super("Producer");
        this.buffer     = buffer;
        this.totalItems = totalItems;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalItems; i++) {
            try {
                buffer.produce(i);
                Thread.sleep(300); // simulate time taken to produce
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[Producer] All items produced. Done.");
    }
}

class Consumer extends Thread {
    private SharedBuffer buffer;
    private int totalItems;

    public Consumer(SharedBuffer buffer, int totalItems) {
        super("Consumer");
        this.buffer     = buffer;
        this.totalItems = totalItems;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalItems; i++) {
            try {
                buffer.consume();
                Thread.sleep(500); // simulate time taken to consume
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("[Consumer] All items consumed. Done.");
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Producer-Consumer Problem (Buffer size = 1) ===");
        System.out.println("Producer adds item when buffer is empty.");
        System.out.println("Consumer removes item when buffer is full.");
        System.out.println("Coordination via wait() and notify().\n");

        int totalItems = 5; // number of items to produce and consume

        SharedBuffer buffer   = new SharedBuffer();
        Thread       producer = new Producer(buffer, totalItems);
        Thread       consumer = new Consumer(buffer, totalItems);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\nProducer-Consumer simulation complete.");
    }
}
