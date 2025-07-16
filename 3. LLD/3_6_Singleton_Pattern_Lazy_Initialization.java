// Lazy Initialization

//Logger class
public class Logger {
    private static volatile Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}


//Usage or main class
public class Main {
    public static void main(String[] args) {
        // Get the Logger instance
        Logger logger = Logger.getInstance();

        // Use the logger to log messages
        logger.log("Application started.");
        logger.log("Processing data...");
        logger.log("Application finished.");

        // Get another instance to show it's the same object
        Logger anotherLogger = Logger.getInstance();

        // Verify that both references point to the same object
        System.out.println(logger == anotherLogger); // Output: true
    }
}


/*

This is called lazy initialization.
The singleton instance is only created if needed (on-demand).
Pros:

The instance is created only if and when it is actually needed; saves resources.
Efficient in multi-threaded environments when using double-checked locking.
Cons:

More complex code.
Care must be taken for thread safety.



	 Eager Initialization ---instance is created--->	When the class loads
   Lazy Initialization	---instance is created---> On first call to getInstance() method

*/
