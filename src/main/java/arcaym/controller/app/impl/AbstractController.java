package arcaym.controller.app.impl;

import java.util.Objects;

import arcaym.controller.app.api.Controller;
import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.view.app.api.View;

/**
 * Abstract implementation of {@link Controller}.
 */
public abstract class AbstractController<T extends View> implements Controller<T> {

    private final ControllerSwitcher switcher;
    private final Runnable backOperation;

    /**
     * Initialize controller with switcher and back operation.
     * 
     * @param switcher controller switcher
     * @param backOperation back operation
     */
    public AbstractController(final ControllerSwitcher switcher, final Runnable backOperation) {
        this.switcher = Objects.requireNonNull(switcher);
        this.backOperation = Objects.requireNonNull(backOperation);
    }

    /**
     * Perform all operations needed before closing.
     */
    protected void dispose() { }

    /**
     * Get controller switcher.
     * 
     * @return controller switcher
     */
    protected final ControllerSwitcher switcher() {
        return this.switcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.dispose();
        this.backOperation.run();
    }

}
