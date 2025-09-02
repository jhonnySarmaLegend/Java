/*   USE inheritance when it is inheritance only

3. Liskov Substitution Principle (LSP)
Subtypes must be substitutable for their base types.
Derived classes must honor the contracts and behaviors promised by the base class.
*/


// Bad: Subclass breaks expected behavior
interface INotifier {
    void send(String to, String message);
}

class EmailNotifier implements INotifier {
    public void send(String to, String msg) { /*…*/ }
}

class PushNotifier implements INotifier {
    public void send(String to, String msg) {
        // Not supported yet
        throw new UnsupportedOperationException("Push not implemented");
    }
}

// Any client doing notifier.send(...) may crash unexpectedly.






//Good: Subclasses honor the contract
// Split responsibilities so no class is forced to throw:
interface INotifier {
    void send(String to, String message);
}

interface IPushNotifier {
    void sendPush(String to, String message);
}

class EmailNotifier implements INotifier { /*…*/ }
class SmsNotifier   implements INotifier { /*…*/ }
class PushNotifier implements IPushNotifier {
    public void sendPush(String to, String message) { /* real push code */ }
}

// Clients use only the interface appropriate to the channel they need.
