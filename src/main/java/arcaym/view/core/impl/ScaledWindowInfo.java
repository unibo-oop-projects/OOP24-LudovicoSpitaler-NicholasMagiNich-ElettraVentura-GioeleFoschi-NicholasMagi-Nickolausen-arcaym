package arcaym.view.core.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Objects;

import arcaym.common.geometry.impl.Point;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link WindowInfo} that scales the window from a given factor.
 */
public class ScaledWindowInfo implements WindowInfo {

    private static final float FULLSCREEEN_SCALE_VALUE = 1f;

    private final Dimension size;
    private final Point ratio;
    private final Scale scale;

    /**
     * Initialize with given scale.
     * 
     * @param scale scale
     */
    public ScaledWindowInfo(final Scale scale) {
        this.scale = Objects.requireNonNull(scale);
        this.size = SwingUtils.scaleDimension(
            Toolkit.getDefaultToolkit().getScreenSize(), 
            scale.value()
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFullscreen() {
        return this.scale.value() == FULLSCREEEN_SCALE_VALUE;
    }

}
