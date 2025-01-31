package arcaym.view.core.impl;

import arcaym.view.core.api.WindowInfo;

/**
 * Abstract implementation of {@link WindowInfo}.
 * It provides full screen info access while leaving the window size infos.
 */
public abstract class AbstractWindowInfo implements WindowInfo {

    private final boolean fullScreen;

    /**
     * Initialize with full screen mode.
     * 
     * @param fullScreen if the window is fullScreen
     */
    public AbstractWindowInfo(final boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFullscreen() {
        return this.fullScreen;
    }

}
