/*
4. Interface Segregation Principle (ISP)
No client should be forced to depend on methods it does not use.
Favor many small, specific interfaces over one large, general-purpose interface.
*/

//Bad: One “fat” interface
interface INotificationClient {
    void sendEmail(String to, String msg);
    void sendSms(String to, String msg);
    void sendPush(String to, String msg);
}

class EmailNotifier implements INotificationClient {
    public void sendEmail(String t, String m) { /*…*/ }
    public void sendSms(String t, String m)  { throw …; }
    public void sendPush(String t, String m) { throw …; }
}


//Good: Many small interfaces
interface IEmailNotifier {
    void sendEmail(String to, String msg);
}

interface ISmsNotifier {
    void sendSms(String to, String msg);
}

interface IPushNotifier {
    void sendPush(String to, String msg);
}

class EmailNotifier implements IEmailNotifier { /*…*/ }
class SmsNotifier   implements ISmsNotifier   { /*…*/ }
class PushNotifier  implements IPushNotifier  { /*…*/ }
