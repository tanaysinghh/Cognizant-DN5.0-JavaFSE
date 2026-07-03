// Singleton Pattern - only one Logger instance can ever exist.
// Everything is in one file; SingletonPatternExample is the public class with main().

class Logger {
    private static final Logger instance = new Logger();

    private Logger() {
        System.out.println("Logger instance created");
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}

public class SingletonLoggerDemo {
    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        log1.log("First message");
        log2.log("Second message");

        System.out.println("Same instance? " + (log1 == log2));
    }
}
