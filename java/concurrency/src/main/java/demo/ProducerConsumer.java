package demo;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        List<Integer> taskQueue = new ArrayList<>();
        Thread producer = new Thread(new Producer(taskQueue, 5), "Producer");
        Thread consumer1 = new Thread(new Consumer(taskQueue), "Consumer1");
        Thread consumer2 = new Thread(new Consumer(taskQueue), "Consumer2");

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}
