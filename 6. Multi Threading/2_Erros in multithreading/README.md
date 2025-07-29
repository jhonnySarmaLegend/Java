
# Synchronized communication

## Overview

In Java multithreading, synchronous communication is used when threads need to coordinate their execution. One thread waits for another to finish its task before proceeding. This is achieved using mechanisms like Thread.join(), synchronized blocks/methods, and wait()/notify().

## Key Concepts

### 1. Blocking Behavior
Synchronous communication involves blocking, where a thread waits for another thread to complete its task.
This ensures that tasks are executed in a specific order.

### . Thread Coordination
Synchronization ensures that only one thread can access a critical section of code at a time.
This prevents race conditions and ensures thread safety.

### 3. Common Mechanisms
Thread.join(): Waits for a thread to finish execution.
synchronized: Ensures mutual exclusion for shared resources.
wait() and notify(): Used for thread coordination.


#### Synchronization communication --> try to access critical section or shared memory during runtime which leads to errors like thread interference error or memory consistency errors 

#### Hence solutions are using synchronized blocks/methods, atomic classes/variables/operations,locks,semaphores...etc or inter thread communication is another way.