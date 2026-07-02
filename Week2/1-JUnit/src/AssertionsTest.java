import static org.junit.Assert.*;
import org.junit.Test;

// Exercise 3: Assertions in JUnit
// Demonstrates the common assertion types.
public class AssertionsTest {

    @Test
    public void testAssertions() {
        assertEquals(5, 2 + 3);        // values are equal
        assertTrue(5 > 3);             // condition is true
        assertFalse(5 < 3);            // condition is false
        assertNull(null);              // reference is null
        assertNotNull(new Object());   // reference is not null
    }
}
