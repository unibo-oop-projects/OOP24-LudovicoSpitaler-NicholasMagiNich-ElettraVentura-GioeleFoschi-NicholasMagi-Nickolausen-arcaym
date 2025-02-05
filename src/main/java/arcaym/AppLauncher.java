package arcaym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.app.MainControllerImpl;
import arcaym.view.app.MainViewImpl;

/**
 * App entry class.
 * Merry Christmas
 */
public final class AppLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLauncher.class);

    private AppLauncher() { }

    /**
     * App entry point.
     * 
     * @param args launch arguments
     */
    public static void main(final String[] args) {
        FileUtils.createMetadataDirectory();
        FileUtils.createSavesDirectory();
        FileUtils.createUserDirectory();

        final var controller = new MainControllerImpl();
        final var view = new MainViewImpl(controller);
        if (view.init()) {
            controller.setView(view);
            LOGGER.info("Application has started!");
        }
    }
}
