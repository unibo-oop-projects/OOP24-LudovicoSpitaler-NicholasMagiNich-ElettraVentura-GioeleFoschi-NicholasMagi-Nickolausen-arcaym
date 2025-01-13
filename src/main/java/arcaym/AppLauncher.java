package arcaym;

import java.util.logging.Logger;

/**
 * App entry class.
 * Merry Christmas
 */
public class AppLauncher {

    private static final Logger LOGGER = Logger.getLogger(AppLauncher.class.getName());

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
        //var controller = new MainController();
        //controller.setView(new MainView(controller));
        final var app = new AppLauncher();
        LOGGER.info(app.getGreeting("World"));
    }
}
