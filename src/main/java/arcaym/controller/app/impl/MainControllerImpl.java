package arcaym.controller.app.impl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

import arcaym.controller.app.api.ExtendedController;
import arcaym.controller.app.api.MainController;
import arcaym.view.app.api.MainView;
import arcaym.view.app.api.View;

/**
 * Implementation of {@link MainController}.
 */
public class MainControllerImpl implements MainController {

    private Optional<MainView> mainView = Optional.empty();
    private final Deque<QueueElement<?, ?>> stack = new LinkedList<>();

    private record QueueElement<V extends View, C extends ExtendedController<V>>(C controller, BiFunction<MainView, C, V> viewCreator) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final MainView view) {
        this.mainView = Optional.of(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        stack.stream()
            .map(QueueElement::controller)
            .forEach(ExtendedController::close);
        System.exit(0);
    }

    public void goBack() {
        if (stack.size() == 1) {
            throw new IllegalStateException("Cannot go back!");
        }
        this.stack.removeFirst().controller.close();
        switchTo(this.stack.removeFirst());
    }

    private <V extends View, C extends ExtendedController<V>> void switchTo(final QueueElement<V,C> element) {
        this.switchTo(element.controller(), element.viewCreator());;
    }

    private <V extends View, C extends ExtendedController<V>> void switchTo(C controller, BiFunction<MainView, C, V> viewCreator) {
        this.stack.addLast(new QueueElement<>(controller, viewCreator));
        final var view = viewCreator.apply(mainView.get(), controller);
        controller.setView(view);
    }
}
