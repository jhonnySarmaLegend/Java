/*
1. Extending the Thread Class
Create a subclass of Thread and override its run() method to define the task to be executed.
Start the thread using the start() method.

Key Points:

Cannot extend other classes since Java does not support multiple inheritance.
Simple and suitable for cases when no other class needs to be extended.
*/

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running via Thread subclass.");
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}


/*
2. Implementing the Runnable Interface
Define a class that implements Runnable and override its run() method.
Pass an instance of the implementing class to the Thread constructor.

Key Points:

Preferred when the class needs to extend another class.
Promotes separation of task (Runnable) and executor (Thread).
*/

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread running via Runnable interface.");
    }
}

public class RunnableExample {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}


/*
3. Using Lambda Expressions (with Runnable)
Simplifies the Runnable implementation with concise syntax using lambdas.

Key Points:

Introduced in Java 8.
Streamlines code for anonymous Runnable implementations.
*/

public class LambdaExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running via Lambda.");
        });
        thread.start();
    }
}
