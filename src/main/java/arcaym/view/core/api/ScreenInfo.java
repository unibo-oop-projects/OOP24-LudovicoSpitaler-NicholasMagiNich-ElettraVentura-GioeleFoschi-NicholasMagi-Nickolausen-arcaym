package arcaym.view.core.api;

import java.awt.Dimension;

import arcaym.common.geometry.impl.Point;

public interface ScreenInfo {
    
    /**
     * Default screen size used as reference for relative calculations.
     */
    Dimension DEFAULT_SIZE = new Dimension(1920, 1080);

    /**
     * Get window size.
     * 
     * @return window size
     */
    Dimension windowSize();

    /**
     * Get ratio between window and default size.
     * 
     * @return ratio
     */
    Point windowRatio();

}
