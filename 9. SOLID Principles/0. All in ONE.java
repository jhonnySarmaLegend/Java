/*
Summary
SRP: One class, one responsibility.
OCP: Extend behavior without modifying existing code.
LSP: Subclasses must fulfill the contracts of their base classes.
ISP: Keep interfaces small and focused; clients only see what they use.
DIP: Depend on abstractions, not concrete implementations.
Applying SOLID leads to code that’s easier to test, maintain, and evolve.
*/



// 1) Single Responsibility, Interface Segregation & Liskov Substitution
//    - IMessageFormatter formats Notifications.
//    - INotifier sends formatted content.
interface IMessageFormatter {
    String format(Notification notification);
}

interface INotifier {
    void send(String recipient, String message);
}

// 2) Open/Closed: new formatters or notifiers can be added without changing existing code.
class PlainTextFormatter implements IMessageFormatter {
    public String format(Notification notification) {
        return notification.getTitle() + "\n" + notification.getBody();
    }
}

class HtmlFormatter implements IMessageFormatter {
    public String format(Notification notification) {
        return "<h1>" + notification.getTitle() + "</h1>"
             + "<p>" + notification.getBody() + "</p>";
    }
}

class EmailNotifier implements INotifier {
    public void send(String recipient, String message) {
        // imagine SMTP logic here…
        System.out.println("Email to " + recipient + ":\n" + message);
    }
}

class SmsNotifier implements INotifier {
    public void send(String recipient, String message) {
        // imagine SMS-API logic here…
        System.out.println("SMS to " + recipient + ": " + message);
    }
}

// Simple data holder (fits LSP: no subclass breaks expectations)
class Notification {
    private final String title;
    private final String body;
    public Notification(String title, String body) {
        this.title = title; this.body = body;
    }
    public String getTitle() { return title; }
    public String getBody()  { return body;  }
}

// 3) Dependency Inversion & 1) Single Responsibility
//    - High-level module depends on abstractions (IMessageFormatter, INotifier), not concretes.
class NotificationService {
    private final IMessageFormatter formatter;
    private final INotifier notifier;

    public NotificationService(IMessageFormatter formatter, INotifier notifier) {
        this.formatter = formatter;
        this.notifier  = notifier;
    }

    // Only one responsibility: orchestrate formatting and sending
    public void notify(String recipient, Notification notification) {
        String formatted = formatter.format(notification);
        notifier.send(recipient, formatted);
    }
}

// Demo wiring
public class NotificationDemo {
    public static void main(String[] args) {
        Notification notif = new Notification("Server Down", "The production server is offline!");
        
        // Injecting dependencies (fulfills DIP)
        NotificationService emailSvc = new NotificationService(
            new HtmlFormatter(),
            new EmailNotifier()
        );
        emailSvc.notify("ops@example.com", notif);

        NotificationService smsSvc = new NotificationService(
            new PlainTextFormatter(),
            new SmsNotifier()
        );
        smsSvc.notify("+1555123456", notif);

        // Later, to add a PushNotifier or MarkdownFormatter, 
        // you simply implement the interface—no existing class needs modification.
    }
}



/*
How This Example Applies SOLID
Single Responsibility Principle (SRP)

NotificationService only orchestrates formatting + sending.
PlainTextFormatter only handles plain-text formatting.
EmailNotifier only handles email delivery, etc.
Open/Closed Principle (OCP)

To support a new output channel (e.g. PushNotifier) or format (e.g. MarkdownFormatter), you simply add a class implementing INotifier or IMessageFormatter. Existing classes remain untouched.
Liskov Substitution Principle (LSP)

Any implementation of INotifier (Email, SMS, Push) can replace one another without breaking NotificationService.
Any IMessageFormatter can be swapped in seamlessly.
Interface Segregation Principle (ISP)

Interfaces are minimal:
IMessageFormatter only has format(...).
INotifier only has send(...).
Clients never depend on methods they don’t use.
Dependency Inversion Principle (DIP)

NotificationService (high-level) depends on the abstractions IMessageFormatter and INotifier rather than concrete classes.
Concrete implementations (EmailNotifier, SmsNotifier, etc.) are injected at runtime.
Result: a flexible, maintainable notification system where adding new formats or channels is as easy as writing one new class—no existing code must be modified.
*/
