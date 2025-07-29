/*
Overview

The Runnable interface represents a task that can be executed by a thread.
It has a single method: void run().
Tasks defined using Runnable do not return a result and cannot throw checked exceptions.

Key Points:

No Return Value: Runnable tasks do not return any result.
Exception Handling: Cannot throw checked exceptions (only unchecked exceptions).
Use Case: Ideal for tasks that perform operations without needing to return a result.
*/

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable task is running on thread: " + Thread.currentThread().getName());
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        // Create a Runnable task
        Runnable task = new MyRunnable();

        // Execute the task using a thread
        Thread thread = new Thread(task);
        thread.start();
    }
}
