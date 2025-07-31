/*
1. Without Dependency Injection (Tightly Coupled Code)
In this approach, the class creates its own dependencies, which leads to tight coupling.
This makes the code hard to test, inflexible, and difficult to maintain.
*/

// Dependency: EmailService
class EmailService {
    void sendEmail(String message) {
        System.out.println("Email sent: " + message);
    }
}

// Dependent Class: NotificationService
class NotificationService {
    private EmailService emailService;

    public NotificationService() {
        // Tightly coupled: Creating the dependency internally
        this.emailService = new EmailService();
    }

    public void sendNotification(String message) {
        emailService.sendEmail(message);
    }
}

// Main Application
public class MainApp {
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService();
        notificationService.sendNotification("Hello, World!");
    }
}

/*
Issues
Tight Coupling: NotificationService is tightly coupled to EmailService. If you want to change the service
(e.g., to SMSService), you must modify the NotificationService class.
Hard to Test: Testing NotificationService requires testing EmailService as well, making unit tests difficult to isolate.
No Flexibility: The code cannot dynamically switch implementations without modifying the source.
*/




// USING DI


/*
2. With Dependency Injection (Loosely Coupled Code)
In this approach, dependencies are injected into the class instead of being created internally.
This promotes loose coupling, testability, and flexibility.
*/

//Step 1: Define Dependency
// Service Interface
interface MessageService {
    void sendMessage(String message);
}

// Implementation: EmailService
class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}


//Step 2: Dependent Class with DI
// Dependent Class: NotificationService
class NotificationService {
    private MessageService messageService;

    // Constructor Injection: Dependency is injected
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendNotification(String message) {
        messageService.sendMessage(message);
    }
}


//Step 3: Main Application
public class MainApp {
    public static void main(String[] args) {
        // Create the dependency
        MessageService emailService = new EmailService();

        // Inject the dependency
        NotificationService notificationService = new NotificationService(emailService);

        // Use the service
        notificationService.sendNotification("Hello, World!");
    }
}




/*
# Flexibility Example (Switching Implementations)
With DI, you can easily switch implementations without modifying the dependent class.
*/
// New Implementation: SMSService
class SMSService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("SMS sent: " + message);
    }
}

public class MainApp {
    public static void main(String[] args) {
        // Switch to SMSService without modifying NotificationService
        MessageService smsService = new SMSService();
        NotificationService notificationService = new NotificationService(smsService);

        // Use the service
        notificationService.sendNotification("Hello, SMS World!");
    }
}



/*
When to Use Dependency Injection
Large Applications: Essential for managing dependencies in complex systems.
Testing: Critical for unit testing and mocking dependencies.
Flexibility: Useful when you need to swap implementations dynamically.
By using Dependency Injection, you can build scalable, maintainable, and testable Java applications.
*/

