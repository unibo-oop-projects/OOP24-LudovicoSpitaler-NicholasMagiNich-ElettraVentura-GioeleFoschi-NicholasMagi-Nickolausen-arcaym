package arcaym.view.core.api;

import java.awt.Dimension;

/**
 * Interface for window attributes.
 */
public interface WindowInfo {

    /**
     * Default window size used as reference for relative calculations.
     */
    Dimension STANDARD_SIZE = new Dimension(1920, 1080);

    /**
     * Get window size.
     * 
     * @return size value
     */
    Dimension size();

    /**
     * Get window scale value.
     * 
     * @return scale value
     */
    float scale();

    /**
     * Get if the window is in fullscreen mode.
     * 
     * @return if the window is fullscreen
     */
    boolean isFullscreen();

}
