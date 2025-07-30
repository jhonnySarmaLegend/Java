//  We are using -->  MOCKITO FRAMEWORK   other --> Easymock , JMock , PowerMock
/*
Mocks in Java are objects that simulate the behavior of real objects in a controlled way. They are commonly used in unit testing
to isolate the code under test from its dependencies. Mocking frameworks like Mockito simplify the creation and usage of mocks.

Here’s a full code example demonstrating how to use mocks in Java with Mockito:

Example: Mocking a Payment Gateway
Scenario:
You’re testing an OrderService that processes orders by charging a payment gateway. You want to mock the PaymentGateway to simulate
its behavior without calling the real implementation.


*/

//Step 1: Add Mockito Dependency --> maven pom.xml/  gradle build.gradle



//Step 2: Define the Interface (PaymentGateway)
public interface PaymentGateway {
    boolean charge(double amount);
}


//Step 3: Write the Test Using Mockito
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void testSuccessfulOrder() {
        // Create a mock of the PaymentGateway
        PaymentGateway paymentGateway = mock(PaymentGateway.class);

        // Stub the behavior of the mock
        when(paymentGateway.charge(100.0)).thenReturn(true);

        // Inject the mock into the OrderService
        OrderService orderService = new OrderService(paymentGateway);

        // Test placing an order
        boolean result = orderService.placeOrder(100.0);

        // Verify the result
        assertTrue(result, "Order should be successful");

        // Verify that the mock was called correctly
        verify(paymentGateway).charge(100.0);
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

Mock Creation
PaymentGateway paymentGateway = mock(PaymentGateway.class); creates a mock object of PaymentGateway.

Stubbing Behavior
when(paymentGateway.charge(100.0)).thenReturn(true); tells the mock to return true when charge(100.0) is called.

Test Execution
The OrderService is tested with the mocked PaymentGateway.

Verification
verify(paymentGateway).charge(100.0); ensures that the charge method was called with the correct argument.
*/



// Example: Mocking a Failed Payment
//To simulate a payment failure, modify the test:

@Test
void testFailedOrder() {
    PaymentGateway paymentGateway = mock(PaymentGateway.class);

    // Stub the mock to simulate a payment failure
    when(paymentGateway.charge(200.0)).thenReturn(false);

    OrderService orderService = new OrderService(paymentGateway);

    boolean result = orderService.placeOrder(200.0);

    assertFalse(result, "Order should fail");

    verify(paymentGateway).charge(200.0);
}




//Example: Verifying Interactions
//Mockito can verify how many times a method was called:

@Test
void testMultipleCharges() {
    PaymentGateway paymentGateway = mock(PaymentGateway.class);

    when(paymentGateway.charge(anyDouble())).thenReturn(true);

    OrderService orderService = new OrderService(paymentGateway);

    orderService.placeOrder(100.0);
    orderService.placeOrder(200.0);

    // Verify that 'charge' was called twice with any double
    verify(paymentGateway, times(2)).charge(anyDouble());
}




/*
Key Features of Mockito
Stubbing: when(...).thenReturn(...)
Verification: verify(...)
Argument Matchers: anyDouble(), eq(...)
Spies: Partial mocks (spy(...))
Annotations: @Mock, @InjectMocks
*/




//Mockito Annotations in Tests
//Mockito provides annotations for cleaner test setups:
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    PaymentGateway paymentGateway;

    @InjectMocks
    OrderService orderService;

    @Test
    void testSuccessfulOrder() {
        when(paymentGateway.charge(100.0)).thenReturn(true);

        boolean result = orderService.placeOrder(100.0);

        assertTrue(result);
        verify(paymentGateway).charge(100.0);
    }
}


/*
Summary
Use mocks to isolate and test specific parts of your code.
Mockito simplifies mock creation, stubbing, and verification.
Combine mocks with annotations (@Mock, @InjectMocks) for cleaner tests.
Mocks are ideal for testing interactions with dependencies.
This approach ensures your unit tests are fast, reliable, and focused on the code under test.
*/
