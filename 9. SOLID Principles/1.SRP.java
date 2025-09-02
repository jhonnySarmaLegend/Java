/*SOLID is an acronym representing five design principles intended to make object-oriented code more maintainable, extensible, 
and robust. Below is an explanation of each principle, with a concise Java example.

1. Single Responsibility Principle (SRP)
A class should have only one reason to change.
Every class/module should focus on a single part of the functionality.


*/

// BAD CODE
public class NotificationService {
    // 1) Formats the notification
    private String format(Notification n) {
        return "<h1>" + n.getTitle() + "</h1><p>" + n.getBody() + "</p>";
    }

    // 2) Sends the notification
    public void notifyByEmail(String to, Notification n) {
        String msg = format(n);
        // … SMTP logic …
    }

    // 3) Logs the notification
    public void log(Notification n) {
        // … write to file …
    }
}







//  GOOD CODE
// Formats messages only
class HtmlFormatter implements IMessageFormatter {
    public String format(Notification n) {
        return "<h1>" + n.getTitle() + "</h1><p>" + n.getBody() + "</p>";
    }
}

// Sends messages only
class EmailNotifier implements INotifier {
    public void send(String recipient, String message) {
        // … SMTP logic …
    }
}

// Logs only
class NotificationLogger {
    public void log(Notification n) {
        // … file or system log …
    }
}

// Orchestrates only
class NotificationService {
    private final IMessageFormatter fmt;
    private final INotifier notifier;
    private final NotificationLogger logger;

    public NotificationService(IMessageFormatter fmt,
                               INotifier notifier,
                               NotificationLogger logger) {
        this.fmt = fmt;
        this.notifier = notifier;
        this.logger = logger;
    }

    public void notify(String recipient, Notification n) {
        String m = fmt.format(n);
        notifier.send(recipient, m);
        logger.log(n);
    }
}
