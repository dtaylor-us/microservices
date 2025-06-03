package us.dtaylor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Main thread started.");

        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit 3 tasks and store Future results
        Future<String> future1 = executor.submit(createTask(1));
        Future<String> future2 = executor.submit(createTask(2));
        Future<String> future3 = executor.submit(createTask(3));

        // Block until results are ready
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());

        // Shutdown the pool
        executor.shutdown();
        System.out.println("Main thread ended.");
    }

    static Callable<String> createTask(int id) {
        return () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Task " + id + " - Step " + i);
                Thread.sleep(100); // Simulate work
            }
            return "Task " + id + " complete!";
        };
    }


}

