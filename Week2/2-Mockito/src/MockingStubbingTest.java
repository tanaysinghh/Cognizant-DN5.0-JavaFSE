import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// Exercise 1: Mocking and Stubbing
// Replace the real ExternalApi with a mock and tell it what to return.
public class MockingStubbingTest {

    @Test
    public void testExternalApi() {
        // create a fake ExternalApi
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        // stub: when getData() is called, return this fixed value
        when(mockApi.getData()).thenReturn("Mock Data");

        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        assertEquals("Mock Data", result);
    }
}
