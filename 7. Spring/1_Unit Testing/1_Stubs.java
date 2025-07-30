/*
Stubs in Java are simplified implementations of objects or methods used in testing. They provide predefined responses to method calls,
allowing you to isolate and test specific parts of your code without relying on real implementations or external dependencies.

Here’s a full code example demonstrating how to create and use stubs in Java:

Example: Stubbing a Payment Gateway
Scenario:
You’re testing an OrderService that processes orders by charging a payment gateway. You want to simulate a successful payment without calling the real payment gateway.
*/



//Step 1: Define the Interface (PaymentGateway)
public interface PaymentGateway {
    boolean charge(double amount);
}


//Step 2: Create a Stub Implementation
public class PaymentGatewayStub implements PaymentGateway {
    @Override
    public boolean charge(double amount) {
        // Simulate a successful payment
        return true;
    }
}


//Step 3: Write the Test Using the Stub
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void testSuccessfulOrder() {
        // Create the stub
        PaymentGateway paymentGateway = new PaymentGatewayStub();

        // Inject the stub into the OrderService
        OrderService orderService = new OrderService(paymentGateway);

        // Test placing an order
        boolean result = orderService.placeOrder(100.0);

        // Verify the result
        assertTrue(result, "Order should be successful");
    }
}


//Step 4: Implement the OrderService
public class OrderService {
    private final PaymentGateway paymentGateway;

    public OrderService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public boolean placeOrder(double amount) {
        return paymentGateway.charge(amount);
    }
}


/*
Explanation

Stub Implementation
PaymentGatewayStub provides a simplified implementation of PaymentGateway. In this case, it always returns true to simulate a successful payment.

Test Isolation
By using PaymentGatewayStub, the OrderServiceTest is isolated from the real PaymentGateway. This ensures the test focuses only on the behavior of OrderService.

Flexibility
Stubs can be adjusted to simulate different scenarios (e.g., failure cases, edge cases) without modifying the real implementation.
*/



