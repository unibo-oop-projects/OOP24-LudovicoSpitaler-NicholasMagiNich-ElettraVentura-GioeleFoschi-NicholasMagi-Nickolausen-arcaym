package arcaym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.controller.app.impl.MainControllerImpl;
import arcaym.view.app.impl.MainViewImpl;

/**
 * App entry class.
 * Merry Christmas
 */
public class AppLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLauncher.class);

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
        final var controller = new MainControllerImpl();
        final var view = new MainViewImpl(controller);
        view.init();
        controller.setView(view);
        LOGGER.info("Application has started!");
    }
}
