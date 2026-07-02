// Singleton Logger - only one instance should ever exist across the app.
public class Logger {

    // the single shared instance, created once when the class first loads
    private static final Logger instance = new Logger();

    // private constructor so nobody outside can call "new Logger()"
    private Logger() {
        System.out.println("Logger instance created");
    }

    // the only way to get hold of the Logger
    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}
