package arcaym.controller.app.impl;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

import arcaym.controller.app.api.ExtendedController;
import arcaym.controller.app.api.MainController;
import arcaym.controller.menu.impl.MenuControllerImpl;
import arcaym.view.app.api.MainView;
import arcaym.view.app.api.View;

/**
 * Implementation of {@link MainController}.
 */
public class MainControllerImpl implements MainController {

    private Optional<MainView> mainView = Optional.empty();
    private final Deque<QueueElement<?, ?>> stack = new LinkedList<>();

    private record QueueElement<V extends View, C extends ExtendedController<V>>(
        C controller, 
        BiFunction<MainView, C, V> viewCreator) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final MainView view) {
        this.mainView = Optional.of(view);
        this.switchTo(new MenuControllerImpl(this::switchTo), MainView::switchToMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        stack.stream()
            .map(QueueElement::controller)
            .forEach(ExtendedController::close);
        Runtime.getRuntime().exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goBack() {
        if (!canGoBack()) {
            throw new IllegalStateException("Cannot go back!");
        }
        this.stack.removeLast().controller.close();
        switchTo(this.stack.removeLast());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canGoBack() {
        return stack.size() > 1;
    }

    private <V extends View, C extends ExtendedController<V>> void switchTo(final QueueElement<V, C> element) {
        this.switchTo(element.controller(), element.viewCreator());
    }

    private <V extends View, C extends ExtendedController<V>> void switchTo(
            final C controller, 
            final BiFunction<MainView, C, V> viewCreator) {
        this.stack.addLast(new QueueElement<>(controller, viewCreator));
        final var view = viewCreator.apply(mainView.get(), controller);
        controller.setView(view);
    }
}
