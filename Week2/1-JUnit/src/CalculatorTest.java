import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

// Exercise 4: Arrange-Act-Assert pattern with @Before / @After.
public class CalculatorTest {

    private Calculator calculator;

    // runs before every test - the "setup" / test fixture
    @Before
    public void setUp() {
        calculator = new Calculator();
        System.out.println("Setup: new Calculator created");
    }

    // runs after every test - the "teardown"
    @After
    public void tearDown() {
        calculator = null;
        System.out.println("Teardown: Calculator cleared");
    }

    @Test
    public void testAdd() {
        // Arrange
        int a = 4, b = 6;
        // Act
        int result = calculator.add(a, b);
        // Assert
        assertEquals(10, result);
    }

    @Test
    public void testSubtract() {
        // Arrange
        int a = 10, b = 3;
        // Act
        int result = calculator.subtract(a, b);
        // Assert
        assertEquals(7, result);
    }
}
