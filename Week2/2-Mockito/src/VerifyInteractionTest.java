import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// Exercise 2: Verifying Interactions
// Confirm the service actually called getData() on the dependency.
public class VerifyInteractionTest {

    @Test
    public void testVerifyInteraction() {
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        service.fetchData();

        // verify getData() was called exactly once
        verify(mockApi).getData();
    }
}
