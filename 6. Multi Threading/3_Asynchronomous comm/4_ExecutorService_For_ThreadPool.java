/*
Benefits of ExecutorService

Thread Pooling: Reuses threads to minimize the overhead of thread creation.
Task Management: Simplifies task submission and execution with methods like submit().
Resource Management: Limits the number of threads to prevent resource exhaustion.
Exception Handling: Provides better mechanisms for handling exceptions in tasks.
Scalability: Adapts to varying workloads dynamically.

Summary
Manual thread management can lead to issues like resource exhaustion, thread creation overhead, lack of control over thread lifecycle,
difficulty in handling exceptions, scalability problems, and complex task queueing. The ExecutorService resolves these issues by 
providing thread pooling, task management, resource management, exception handling, and scalability. This makes it a more robust 
and efficient approach for concurrent task execution in Java.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileProcessingExample {
    public static void main(String[] args) {
        // Simulate a list of files to process
        String[] files = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"};

        // Create a fixed-size thread pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit tasks for each file
        for (String file : files) {
            executor.submit(new FileProcessor(file));
        }

        // Shutdown the executor service
        executor.shutdown();

        // Wait for all tasks to complete
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All files processed.");
    }
}

class FileProcessor implements Runnable {
    private String fileName;

    public FileProcessor(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Processing file: " + fileName + " on thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate file processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed processing file: " + fileName);
    }
}

/* Output Example

Processing file: file1.txt on thread: pool-1-thread-1
Processing file: file2.txt on thread: pool-1-thread-2
Completed processing file: file1.txt
Processing file: file3.txt on thread: pool-1-thread-1
Completed processing file: file2.txt
Processing file: file4.txt on thread: pool-1-thread-2
Completed processing file: file3.txt
Processing file: file5.txt on thread: pool-1-thread-1
Completed processing file: file4.txt
Completed processing file: file5.txt
All files processed.

*/

/*
Explanation

ThreadPool Creation:
A fixed thread pool with 2 threads is created using Executors.newFixedThreadPool(2).
This ensures that only 2 files are processed concurrently at any given time.

Task Submission:
Each file is processed by submitting a FileProcessor task to the ExecutorService.
The tasks are executed by the threads in the pool.

File Processing:
The FileProcessor class implements Runnable and simulates file processing using Thread.sleep().

Shutdown:
The executor.shutdown() method initiates an orderly shutdown of the thread pool.
awaitTermination() ensures the program waits for all tasks to complete before exiting.
*/
