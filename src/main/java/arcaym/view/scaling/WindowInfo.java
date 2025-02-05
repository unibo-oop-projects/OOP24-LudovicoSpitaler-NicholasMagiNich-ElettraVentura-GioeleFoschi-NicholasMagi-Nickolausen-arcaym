package arcaym.view.scaling;

import java.awt.Dimension;

/**
 * Interface for window attributes.
 */
public interface WindowInfo {

    /**
     * @return window size
     */
    Dimension size();

    /**
     * @return window scale
     */
    float scale();

    /**
     * @return if the window is fullscreen
     */
    boolean isFullscreen();

}
