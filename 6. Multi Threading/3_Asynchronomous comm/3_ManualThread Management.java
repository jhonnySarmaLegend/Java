/*
Manual Thread Management
In manual thread management, you create and manage threads explicitly without using higher-level abstractions like ExecutorService.
Here’s how you could rewrite the above example using manual thread management
*/

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


public class FileProcessingExampleManualThreads {
    public static void main(String[] args) {
        // Simulate a list of files to process
        String[] files = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"};
        
      // Create and start threads for each file
        for (String file : files) {
            Thread thread = new Thread(new FileProcessor(file));//new FileProcessor(file) runnable interface implementation by the class
            thread.start();
        }
       
      // Wait for all threads to complete
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().startsWith("Thread-")) { // Filter out main and other threads
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("All files processed.");
    }
}


/*
Issues with Manual Thread Management
 
Resource Exhaustion:
Problem: Creating too many threads can exhaust system resources (e.g., memory, CPU). Each thread consumes memory for 
its stack and incurs overhead for context switching.
Solution: ExecutorService uses a thread pool, limiting the number of threads and managing resource usage efficiently.

Thread Creation Overhead:
Problem: Creating and destroying threads repeatedly is expensive in terms of performance.
Solution: ExecutorService reuses threads from a pool, reducing the overhead of thread creation and destruction.

Lack of Control Over Thread Lifecycle:
Problem: With manual threads, you have to manage thread lifecycle (creation, starting, stopping) explicitly, which can lead to bugs and complexity.
Solution: ExecutorService abstracts thread lifecycle management, providing methods like shutdown() and awaitTermination() for controlled termination.

Difficulty in Handling Exceptions:
Problem: Handling exceptions in manually managed threads can be challenging, as uncaught exceptions can terminate threads silently.
Solution: ExecutorService provides mechanisms to handle exceptions more gracefully, such as Future objects that can capture and handle exceptions.

Scalability Issues:
Problem: Manual thread management does not scale well for applications with varying workloads, as you need to adjust the number of threads manually.
Solution: ExecutorService offers dynamic thread pools (e.g., CachedThreadPool) that adjust the number of threads based on workload.

Task Queueing and Scheduling:
Problem: Manually managing task queueing and scheduling can be complex and error-prone.
Solution: ExecutorService provides built-in task queueing and scheduling mechanisms, allowing for efficient task execution.
*/
