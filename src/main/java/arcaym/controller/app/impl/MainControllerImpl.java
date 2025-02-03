package arcaym.controller.app.impl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.BiFunction;

import arcaym.controller.app.api.ExtendedController;
import arcaym.controller.app.api.MainController;
import arcaym.view.app.api.MainView;
import arcaym.view.app.api.View;

/**
 * Implementation of {@link MainController}.
 */
public class MainControllerImpl implements MainController {

    private final MainView mainView;
    private Deque<QueueElement<?, ?>> stack = new LinkedList<>();

    private record QueueElement<V extends View, C extends ExtendedController<V>>(C controller, BiFunction<MainView, C, V> viewCreator) {}

    /**
     * Default constructor.
     * 
     * @param switcher
     * @param backOperation
     */
    public MainControllerImpl(final MainView mainView) {
        this.mainView = Objects.requireNonNull(mainView);
    }

    public void start() {
        this.mainView.switchToMenu(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        
    }

}
