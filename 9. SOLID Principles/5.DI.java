/*
5. Dependency Inversion Principle (DIP)
High-level modules should not depend on low-level modules; both should depend on abstractions.
Abstractions should not depend on details; details should depend on abstractions.
*/

//Bad: High-level class new’s low-level detail
class NotificationService {
    public void notify(String recipient, Notification n) {
        EmailFormatter fmt = new EmailFormatter();
        EmailNotifier notifier = new EmailNotifier();
        String msg = fmt.format(n);
        notifier.send(recipient, msg);
    }
}

//Good: Depend on interfaces, inject implementations
class NotificationService {
    private final IMessageFormatter fmt;
    private final INotifier notifier;

    public NotificationService(IMessageFormatter fmt,
                               INotifier notifier) {
        this.fmt = fmt;
        this.notifier = notifier;
    }

    public void notify(String recipient, Notification n) {
        String msg = fmt.format(n);
        notifier.send(recipient, msg);
    }
}

// Wiring up in main or via DI:
IMessageFormatter fmt = new HtmlFormatter();
INotifier notifier      = new EmailNotifier();
NotificationService svc  = new NotificationService(fmt, notifier);
