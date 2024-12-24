package arcaym;

import java.util.logging.Logger;

/**
 * App entry class.
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Get greeting message for subject.
     * 
     * @param subject name to greet
     * @return greeting message
     */
    public String getGreeting(final String subject) {
        return "Hello, " + subject;
    }

    /**
     * App entry point.
     * 
     * @param args launch arguments
     */
    public static void main(final String[] args) {
        final var app = new App();
        LOGGER.info(app.getGreeting("World"));
    }
}
