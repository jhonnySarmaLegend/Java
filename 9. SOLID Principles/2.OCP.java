/*
2. Open/Closed Principle (OCP)
Software entities should be open for extension but closed for modification.
You should be able to add new behavior without changing existing code.
*/

// Bad: Changing code for every new channel
class NotificationService {
    public void notify(String to, Notification n, String channel) {
        if (channel.equals("EMAIL")) {
            // … send email …
        } else if (channel.equals("SMS")) {
            // … send sms …
        } // add another else-if for each new channel…
    }
}



//  Good: Use polymorphism
interface INotifier {
    void send(String to, String message);
}

class EmailNotifier implements INotifier { /*…*/ }
class SmsNotifier   implements INotifier { /*…*/ }
// adding PushNotifier later requires no changes here

class NotificationService {
    private final INotifier notifier;
    public NotificationService(INotifier notifier) {
        this.notifier = notifier;
    }
    public void notify(String to, Notification n) {
        String m = /* format as needed */;
        notifier.send(to, m);
    }
}
