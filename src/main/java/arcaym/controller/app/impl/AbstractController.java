package arcaym.controller.app.impl;

import java.util.Objects;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.controller.app.api.Controller;
import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.view.app.api.View;

/**
 * Abstract implementation of {@link Controller}.
 * It provides the controller switcher access while leaving the logic.
 * 
 * @param <T> associated view type
 */
public abstract class AbstractController<T extends View> implements Controller<T> {

    private final ControllerSwitcher switcher;
    private final Runnable backOperation;
    private Optional<T> view = Optional.empty();

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
     * Get attached view.
     * 
     * @return view
     */
    protected final T view() {
        return Optionals.orIllegalState(this.view, "View has not been set");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final T view) {
        this.view = Optional.of(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() { }

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
