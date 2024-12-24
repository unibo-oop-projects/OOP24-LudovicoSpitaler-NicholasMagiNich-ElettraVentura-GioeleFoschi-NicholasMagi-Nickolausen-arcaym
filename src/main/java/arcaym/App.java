package arcaym;

public class App {

    public static String getGreeting(final String subject) {
        return "Hello, " + subject;
    }

    public static void main(String[] args) {
        System.out.println(getGreeting("World"));
    }
}
