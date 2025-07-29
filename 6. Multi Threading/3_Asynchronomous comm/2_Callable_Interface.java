/*
Overview

The Callable interface represents a task that can return a result and throw checked exceptions.
It has a single method: V call() throws Exception, where V is the return type of the task.

Key Points:

Return Value: Callable tasks can return a result of a specified type.
Exception Handling: Can throw checked exceptions.
Use Case: Ideal for tasks that need to return a result or handle exceptions.
*/

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Callable task is running on thread: " + Thread.currentThread().getName());
        Thread.sleep(1000); // Simulate task execution time
        return "Task Completed";
    }
}

public class CallableExample {
    public static void main(String[] args) throws Exception {
        // Create a Callable task
        Callable<String> task = new MyCallable();

        // Execute the task using ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task);

        // Get the result of the task
        String result = future.get();
        System.out.println("Callable Task Result: " + result);

        // Shutdown the executor service
        executor.shutdown();
    }
}


/*
When to Use Which?

Use Runnable:
For tasks that perform operations without returning a result.
When you don’t need to handle checked exceptions.

Use Callable:
For tasks that need to return a result.
When you need to handle checked exceptions.


Real-World Use Cases

Runnable
Performing background tasks like logging, monitoring, or cleanup operations.
Updating the UI in Swing or JavaFX applications.

Callable
Fetching data from a database or an external API.
Performing computations that return a result, such as calculating the sum of a large dataset.
*/
