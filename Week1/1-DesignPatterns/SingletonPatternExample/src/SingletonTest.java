// Checks that both references point to the SAME Logger object.
public class SingletonTest {
    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        log1.log("First message");
        log2.log("Second message");

        // true means it really is a single shared instance
        System.out.println("Same instance? " + (log1 == log2));
    }
}
