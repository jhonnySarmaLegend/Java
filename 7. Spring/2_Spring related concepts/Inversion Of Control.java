//1. Without IoC (Tightly Coupled)
// Concrete dependency
class EmailService {
    void sendEmail(String message) {
        System.out.println("Email sent: " + message);
    }
}

// Client that manages its own dependency
class NotificationService {
    private EmailService emailService;

    public NotificationService() {
        // Direct instantiation → tight coupling
        this.emailService = new EmailService();
    }

    public void sendNotification(String message) {
        emailService.sendEmail(message);
    }
}

// Main application
public class MainApp {
    public static void main(String[] args) {
        NotificationService svc = new NotificationService();
        svc.sendNotification("Hello, World!");
    }
}

/*
Drawbacks

NotificationService is tightly coupled to EmailService.
Swapping to another channel (e.g. SMS) requires editing NotificationService.
Hard to unit-test in isolation.
*/




//2. With IoC (Spring-Managed Beans)


//a) Define Interface & Implementations
// Service contract
public interface MessageService {
    void sendMessage(String message);
}

//a) Concrete implementation
@Component
public class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

//b) Declare Dependent Bean
@Component
public class NotificationService {
    private final MessageService messageService;

    // Constructor Injection
    @Autowired
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendNotification(String message) {
        messageService.sendMessage(message);
    }
}

//c)Configuration & Bootstrap -->  SPRING not SPRING Boot
@Configuration
@ComponentScan("com.example")
public class AppConfig { }

public class MainApp {
    public static void main(String[] args) {
        // 1. Initialize IoC container
        AnnotationConfigApplicationContext ctx =
            new AnnotationConfigApplicationContext(AppConfig.class);

        // 2. Retrieve the NotificationService bean
        NotificationService svc = ctx.getBean(NotificationService.class);

        // 3. Use it
        svc.sendNotification("Hello, IoC World!");

        ctx.close();
    }
}
