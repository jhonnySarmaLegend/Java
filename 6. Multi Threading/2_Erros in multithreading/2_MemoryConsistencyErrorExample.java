/*'
Memory Consistency Errors
Definition:
Memory consistency errors occur in multi-threaded programs when different threads have 
inconsistent views of shared data. This happens because of the way modern CPUs and memory systems work, 
such as caching, reordering of instructions, and optimizations that can lead to threads seeing stale or outdated values of shared variables.
*/

public class MemoryConsistencyErrorExample {
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = () -> {
            flag = true; // Thread 1 updates flag
        };

        Runnable task2 = () -> {
            while (!flag) {
                // Thread 2 spins until flag becomes true
            }
            System.out.println("Flag is true!");
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/*
Explanation

Expected Behavior:
Thread t1 sets flag to true, and Thread t2 should detect this change and print "Flag is true!".

Observed Issue:
Thread t2 may never see the updated value of flag (despite t1 setting it to true) due to memory consistency errors.
This happens because Thread t2 may be reading flag from its local cache, which is not updated with the latest value from Thread t1.

Why Memory Consistency Errors Occur

Caching:
Each thread may have its own cached copy of shared variables, leading to stale data.
Instruction Reordering:
Compilers and CPUs may reorder instructions for optimization, causing inconsistent views of memory.

Visibility:
Changes made by one thread may not be visible to other threads immediately due to delays in memory updates.
*/




// 1. Use volatile Keyword:

//The volatile keyword ensures that reads and writes to the variable are always from main memory, making changes visible to all threads.
//For compound operations (e.g., i++), you need synchronization, as volatile doesn't guarantee atomicity.

private static volatile boolean flag = false;



//2. Use synchronized Methods or Blocks:

//Synchronization ensures mutual exclusion and memory consistency.
// No ability to try acquiring the lock without blocking.

private static boolean flag = false;
private static final Object lock = new Object();

public static void main(String[] args) throws InterruptedException {
    Runnable task1 = () -> {
        synchronized (lock) {
            flag = true;
        }
    };

    Runnable task2 = () -> {
        synchronized (lock) {
            while (!flag) {
                // Wait until flag becomes true
            }
        }
        System.out.println("Flag is true!");
    };

    Thread t1 = new Thread(task1);
    Thread t2 = new Thread(task2);

    t1.start();
    t2.start();

    t1.join();
    t2.join();
}





// 3. Use Locks (ReentrantLock):

//ReentrantLock provides explicit locking and ensures memory consistency.
/*
A powerful alternative to synchronized blocks/methods. 
It allows for
more flexible locking mechanisms
tryLock(), tryLock(long time, TimeUnit unit), and lockInterruptibly()
and provides additional functionalities such as fairness policies and interruptible lock acquisition.

*/
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private static boolean flag = false;
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = () -> {
            lock.lock();
            try {
                flag = true; // Thread 1 updates flag
            } finally {
                lock.unlock();
            }
        };

        Runnable task2 = () -> {
            lock.lock();
            try {
                while (!flag) {
                    // Thread 2 spins until flag becomes true
                }
            } finally {
                lock.unlock();
            }
            System.out.println("Flag is true!");
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}



/*
When to Use Which Fix

Use volatile:

For simple shared variables (like flags).
When atomicity is not required.

Use synchronized:

For complex shared state modifications.
When atomicity and mutual exclusion are required.

Use Atomic Classes:

For simple shared state updates (e.g., counters, flags).
When high performance and atomicity are required.

Use Locks:

For advanced locking scenarios (e.g., timed locks, fairness).
When fine-grained control is needed.

Conclusion
Memory consistency errors arise from inconsistent views of shared data in multi-threaded programs.
They can be fixed using techniques like volatile, synchronized, atomic classes, or locks. Choose the 
appropriate solution based on the specific requirements of your application to ensure thread safety and correctness.
*/
