/*
1. Thread Interference Errors
Definition:
Thread interference occurs in multi-threaded programs when two or more threads access and modify shared data
simultaneously in such a way that the final results become unpredictable or incorrect. This happens due to the
absence of proper coordination between threads and results in unintended interactions.

How Thread Interference Causes Problems:
Preemption: If a thread is preempted in the middle of an operation, another thread may modify the data, leading to
incorrect results when the preempted thread resumes.

Non-Atomic Operations: Compound actions like value = value + 1 involve multiple steps (reading value, adding 1, writing it back),
and interference may occur if threads interleave these steps.

operations involved while incrementing a number in instruction level

get count
temp->count+1
count=temp


*/

class Counter {
    private int count = 0;

    public void increment() {
        count = count + 1; // Read-modify-write (not thread-safe)
    }

    public void decrement() {
        count = count - 1; // Read-modify-write (not thread-safe)
    }

    public int getCount() {
        return count;
    }
}

public class ThreadInterferenceExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Thread to increment the counter 1000 times
        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        // Thread to decrement the counter 1000 times
        Thread decrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });

        // Start both threads
        incrementThread.start();
        decrementThread.start();

        // Wait for both threads to finish
        incrementThread.join();
        decrementThread.join();

        // Output the final count
        System.out.println("Final count: " + counter.getCount()); 
        // ran multiple times--> 0 , 0 ,-156 ,1867
    }
}



/*
Expected Behavior:

Since the increment and decrement operations are equal (both executed 1000 times), the final count should ideally be 0.
Observed Issue:

Due to thread interference, the final count may not be 0 and will vary across runs.
This is because the increment (count = count + 1) and decrement (count = count - 1) are not atomic. Multiple threads may 
read and overwrite the count variable simultaneously.
Why This Happens:

The operations consist of three steps:
Read the current value of count.
Modify the value (add/subtract).
Write the updated value back to memory.
If one thread is preempted after the "read" step, another thread may overwrite the value, causing incorrect results.
*/



// SOLUTION

//1. Atomic Integer
import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Atomic increment
    }

    public void decrement() {
        count.decrementAndGet(); // Atomic decrement
    }

    public int getCount() {
        return count.get();
    }
}

public class AtomicExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        };

        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(decrementTask);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount()); // Always 0
    }
}




// 2.Synchronization - Synchronized Methods
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count = count + 1; // Critical section
    }

    public synchronized void decrement() {
        count = count - 1; // Critical section
    }

    public synchronized int getCount() {
        return count;
    }
}

public class SynchronizedExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        };

        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(decrementTask);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount()); // Always 0
    }
}


// 2. Synchronization - Synchronized Blocks
class Counter {
    private int count = 0;
    private final Object lock = new Object(); // A custom lock object to synchronize on

    public void increment() {
        synchronized (lock) { // Synchronizing only the critical section
            count = count + 1;
        }
    }

    public void decrement() {
        synchronized (lock) { // Synchronizing only the critical section
            count = count - 1;
        }
    }

    public int getCount() {
        synchronized (lock) { // Synchronizing to ensure consistent read
            return count;
        }
    }
}

public class SynchronizedBlockExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        };

        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(decrementTask);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Output the final count
        System.out.println("Final count: " + counter.getCount()); // Always 0
    }
}


// 3. Locks
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count = count + 1; // Critical section
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            count = count - 1; // Critical section
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}

public class LockExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        };

        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(decrementTask);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount()); // Always 0
    }
}
