/*
Below is the Spring Boot–equivalent of your plain‐Spring constructor-injection example. We’ll use @SpringBootApplication to
bootstrap and CommandLineRunner to invoke your service on startup.
*/

// src/main/java/com/example/demo/MessageService.java
package com.example.demo;

public interface MessageService {
    void sendMessage(String message);
}


// src/main/java/com/example/demo/EmailService.java
package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}


// src/main/java/com/example/demo/NotificationService.java
package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    private final MessageService messageService;

    // Constructor Injection (no @Autowired needed on single-constructor in recent Spring versions)
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendNotification(String message) {
        messageService.sendMessage(message);
    }
}


// src/main/java/com/example/demo/DemoApplication.java
package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class DemoApplication implements CommandLineRunner {
    private final NotificationService notificationService;

    // Constructor injection of NotificationService
    public DemoApplication(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        notificationService.sendNotification("Hello, Spring Boot IoC World!");
    }
}


/*
Key Points

@SpringBootApplication replaces your manual AppConfig + AnnotationConfigApplicationContext.
Spring Boot auto-scans @Service and @Component beans.
You can omit @Autowired on a single constructor.
CommandLineRunner runs logic after context startup—no manual getBean(...) needed.
*/
