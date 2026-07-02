import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Exercise 1: Logging Error Messages and Warning Levels using SLF4J.
public class LoggingExample {

    // one logger per class, named after the class
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        logger.info("Application started");          // normal info message
        logger.warn("This is a warning message");    // warning level

        try {
            int result = 10 / 0;   // deliberately cause an error
        } catch (ArithmeticException e) {
            logger.error("An error occurred: " + e.getMessage()); // error level
        }

        logger.info("Application finished");
    }
}
