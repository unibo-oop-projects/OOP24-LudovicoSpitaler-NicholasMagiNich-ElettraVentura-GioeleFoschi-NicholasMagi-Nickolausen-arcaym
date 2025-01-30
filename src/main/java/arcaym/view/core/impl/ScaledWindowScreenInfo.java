package arcaym.view.core.impl;

import java.awt.Dimension;
import java.awt.Toolkit;

import arcaym.common.geometry.impl.Point;
import arcaym.view.core.api.ScreenInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link ScreenInfo} that scales the window from a given factor.
 */
public class ScaledWindowScreenInfo implements ScreenInfo {

    private final Dimension windowSize;
    private final Point windowRatio;

    /**
     * Initialize with given window scale.
     * 
     * @param windowScale scale factor
     */
    public ScaledWindowScreenInfo(final float windowScale) {
        this.windowSize = SwingUtils.scaleDimension(
            Toolkit.getDefaultToolkit().getScreenSize(), 
            windowScale
        );
        this.windowRatio = Point.of(
            this.windowSize.getWidth() / DEFAULT_SIZE.getWidth(),
            this.windowSize.getHeight() / DEFAULT_SIZE.getHeight()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension windowSize() {
        return this.windowSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point windowRatio() {
        return this.windowRatio;
    }

}
