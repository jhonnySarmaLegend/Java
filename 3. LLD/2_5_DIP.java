// WRONG

// BAD EXAMPLE - Violates DIP

// Low-level module: The specific device (concrete implementation)
class LightBulb {
    public void turnOn() {
        System.out.println("LightBulb: is ON");
    }

    public void turnOff() {
        System.out.println("LightBulb: is OFF");
    }
}

// High-level module: The controller (depends directly on LightBulb)
class Switch {
    private LightBulb bulb; // Direct dependency on a concrete class

    public Switch(LightBulb bulb) {
        this.bulb = bulb;
    }

    public void operate(boolean turnOn) {
        if (turnOn) {
            bulb.turnOn();
        } else {
            bulb.turnOff();
        }
    }
}

public class MainBadDIP {
    public static void main(String[] args) {
        LightBulb bulb = new LightBulb();
        Switch lightSwitch = new Switch(bulb);

        System.out.println("--- Operating Light Bulb ---");
        lightSwitch.operate(true);  // Turn on
        lightSwitch.operate(false); // Turn off
    }
}



//CORRECT

// GOOD EXAMPLE - DIP Compliant

// 1. Abstraction (Interface) - Both high-level and low-level will depend on this
interface Switchable {
    void turnOn();
    void turnOff();
}

// 2. Low-level module: Concrete devices (now depend on the Abstraction)
class LightBulb implements Switchable { // Implements the abstraction
    @Override
    public void turnOn() {
        System.out.println("LightBulb: is ON");
    }

    @Override
    public void turnOff() {
        System.out.println("LightBulb: is OFF");
    }
}

class Fan implements Switchable { // Another concrete device, also implements the abstraction
    @Override
    public void turnOn() {
        System.out.println("Fan: is Spinning");
    }

    @Override
    public void turnOff() {
        System.out.println("Fan: is Stopped");
    }
}

// 3. High-level module: The controller (now depends on the Abstraction)
class Switch {
    private Switchable device; // Dependency on an abstraction (interface)

    // Dependency Injection: The device is injected through the constructor
    public Switch(Switchable device) {
        this.device = device;
    }

    public void operate(boolean turnOn) {
        if (turnOn) {
            device.turnOn(); // Delegates to the abstraction
        } else {
            device.turnOff(); // Delegates to the abstraction
        }
    }
}

public class MainGoodDIP {
    public static void main(String[] args) {
        // Wiring them up (usually done by a framework or main method)

        // Control a LightBulb
        Switchable bulb = new LightBulb(); // Low-level detail
        Switch lightSwitch = new Switch(bulb); // High-level Switch depends on abstraction

        System.out.println("--- Operating Light Bulb ---");
        lightSwitch.operate(true);
        lightSwitch.operate(false);

        System.out.println("\n--- Operating Fan ---");
        // Control a Fan (without changing the Switch class!)
        Switchable fan = new Fan(); // Different low-level detail
        Switch fanSwitch = new Switch(fan); // High-level Switch still depends on abstraction

        fanSwitch.operate(true);
        fanSwitch.operate(false);
    }
}
