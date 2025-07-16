
// Eager initialization --> thread safe

// Logging class
public class Logger {
    // 1. Make the constructor private so no one can instantiate from outside
    private Logger() {}

    // 2. Create the single instance (eager initialization)
    private static final Logger INSTANCE = new Logger();

    // 3. Provide a global point of access
    public static Logger getInstance() {
        return INSTANCE;
    }

    // Sample log method
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}


// usage
public class Main {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Hello from logger1");
        logger2.log("Hello from logger2");

        // Both logger1 and logger2 refer to the same instance
        System.out.println(logger1 == logger2); // Output: true
    }
}



/*

This is called eager initialization (or sometimes “early initialization”).
The singleton instance exists whether or not it is ever used.
Pros:

Simple and thread-safe (guaranteed by Java class loading).
No need for synchronization.
Cons:

The instance is created even if it is never used (may waste memory if the object is expensive and rarely used).

*/
