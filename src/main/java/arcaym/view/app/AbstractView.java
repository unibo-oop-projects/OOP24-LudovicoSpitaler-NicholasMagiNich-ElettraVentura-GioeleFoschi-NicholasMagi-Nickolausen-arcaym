package arcaym.view.app;

import java.util.Objects;

import arcaym.controller.app.Controller;

/**
 * Abstract implementation of {@link View}.
 * It provides controller access while leaving the logic.
 * 
 * @param <C> controller type
 */
public class AbstractView<C extends Controller> implements View {

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
