package arcaym.controller.app.impl;

import java.util.Objects;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.api.ExtendedController;
import arcaym.view.app.api.View;

/**
 * Abstract implementation of {@link ExtendedController}.
 * It provides the controller switcher access while leaving the logic.
 * 
 * @param <T> associated view type
 */
public abstract class AbstractController<T extends View> implements ExtendedController<T> {

    private final ControllerSwitcher switcher;
    private Optional<T> view = Optional.empty();

    /**
     * Initialize controller with switcher.
     * 
     * @param switcher controller switcher
     */
    public AbstractController(final ControllerSwitcher switcher) {
        this.switcher = Objects.requireNonNull(switcher);
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
    public void close() { }

}
