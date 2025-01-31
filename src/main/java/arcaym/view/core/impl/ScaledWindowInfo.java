package arcaym.view.core.impl;

import java.awt.Dimension;
import java.awt.Toolkit;

import arcaym.common.geometry.impl.Point;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link WindowInfo} that scales the window from a given factor.
 */
public class ScaledWindowInfo extends AbstractWindowInfo {

    private final Dimension size;
    private final Point ratio;

    /**
     * Initialize with given window scale.
     * 
     * @param windowScale scale factor
     * @param fullScreen if the window is fullScreen
     */
    public ScaledWindowInfo(final float windowScale, final boolean fullScreen) {
        super(fullScreen);
        this.size = SwingUtils.scaleDimension(
            Toolkit.getDefaultToolkit().getScreenSize(), 
            windowScale
        );
        this.ratio = Point.of(
            this.size.getWidth() / STANDARD_SIZE.getWidth(),
            this.size.getHeight() / STANDARD_SIZE.getHeight()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension size() {
        return new Dimension(this.size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point ratio() {
        return this.ratio;
    }

}
