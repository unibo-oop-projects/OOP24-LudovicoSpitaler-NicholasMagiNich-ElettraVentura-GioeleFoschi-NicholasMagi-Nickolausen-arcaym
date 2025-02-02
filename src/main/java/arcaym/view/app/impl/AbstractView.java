package arcaym.view.app.impl;

import java.util.Objects;

import arcaym.controller.app.api.ControllerInfo;
import arcaym.view.app.api.View;

/**
 * Abstract implementation of {@link View}.
 * It provides controller access while leaving the logic.
 * 
 * @param <C> controller type
 */
public class AbstractView<C extends ControllerInfo> implements View {

    private final C controller;

    /**
     * Initialize with given controller.
     * 
     * @param controller controller
     */
    public AbstractView(final C controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * Get controller.
     * 
     * @return controller
     */
    protected final C controller() {
        return this.controller;
    }

}
