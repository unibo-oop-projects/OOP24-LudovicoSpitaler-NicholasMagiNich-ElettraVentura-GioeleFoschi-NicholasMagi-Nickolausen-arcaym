package arcaym.view.core;

import java.util.Optional;

import javax.swing.JComponent;

import arcaym.view.scaling.WindowInfo;

/**
 * Representation of a general view component with a child component.
 * 
 * @param <T> type of the component
 */
public interface ParentComponent<T extends JComponent> extends ViewComponent<T> {

    /**
     * Builds the component.
     * 
     * @param window window info
     * @param childComponent child component
     * @return resulting component
     */
    T build(WindowInfo window, Optional<JComponent> childComponent);

    /**
     * Builds the component without a child.
     * 
     * @param window window info
     * @return resulting component
     */
    @Override
    default T build(final WindowInfo window) {
        return this.build(window, Optional.empty());
    }

    /**
     * Builds the component without a swing component child.
     * 
     * @param window window info
     * @param childComponent child component
     * @return resulting component
     */
    default T build(final WindowInfo window, final JComponent childComponent) {
        return this.build(window, Optional.ofNullable(childComponent));
    }

    /**
     * Builds the component without a view component child.
     * 
     * @param window window info
     * @param childComponent child component
     * @return resulting component
     */
    default T build(final WindowInfo window, final ViewComponent<?> childComponent) {
        return this.build(
            window, 
            Optional.ofNullable(childComponent).map(c -> c.build(window))
        );
    }

}
