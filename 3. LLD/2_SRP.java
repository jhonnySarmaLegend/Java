// WRONG

class UserManager {
    public void createUserInDatabase(String username, String email) {
        System.out.println("UserManager: Saving user " + username + " to the database.");
        // --- Database interaction code here ---
        // e.g., INSERT INTO users (username, email) VALUES (?, ?);
    }

    public void sendWelcomeEmail(String username, String email) {
        System.out.println("UserManager: Sending welcome email to " + email + " for " + username + ".");
        // --- Email sending API code here ---
        // e.g., call SMTP server, compose email body
    }
}

public class MainBadExample {
    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.createUserInDatabase("alice", "alice@example.com");
        manager.sendWelcomeEmail("alice", "alice@example.com");
    }
}


//CORRECT

// 1. UserRepository: Handles saving/loading user data (Single Responsibility: Persistence)
class UserRepository {
    public void save(String username, String email) {
        System.out.println("UserRepository: Saving user " + username + " to the database.");
        // --- Database interaction code here ---
        // e.g., INSERT INTO users (username, email) VALUES (?, ?);
    }
}

// 2. EmailService: Handles sending emails (Single Responsibility: Notification)
class EmailService {
    public void sendWelcomeEmail(String username, String email) {
        System.out.println("EmailService: Sending welcome email to " + email + " for " + username + ".");
        // --- Email sending API code here ---
        // e.g., call SMTP server, compose email body
    }
}

public class MainGoodExample {
    public static void main(String[] args) {
        // Create instances of the new, specialized classes
        UserRepository userRepo = new UserRepository();
        EmailService emailService = new EmailService();

        // Use each class for its specific responsibility
        userRepo.save("bob", "bob@example.com");          // User persistence
        emailService.sendWelcomeEmail("bob", "bob@example.com"); // Email notification
    }
}
