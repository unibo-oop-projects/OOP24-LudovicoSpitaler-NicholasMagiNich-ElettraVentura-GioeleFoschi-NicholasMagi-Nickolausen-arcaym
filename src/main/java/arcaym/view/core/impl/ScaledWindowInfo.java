package arcaym.view.core.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Objects;

import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link WindowInfo} that scales the window from a given factor.
 */
public class ScaledWindowInfo implements WindowInfo {

    private static final float FULLSCREEEN_SCALE_VALUE = 1f;

    private final Dimension size;
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
    public float scale() {
        return this.scale.value();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFullscreen() {
        return this.scale.value() == FULLSCREEEN_SCALE_VALUE;
    }

}
