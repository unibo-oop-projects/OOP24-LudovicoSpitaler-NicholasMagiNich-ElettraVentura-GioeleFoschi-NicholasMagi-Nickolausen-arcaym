package arcaym.view.api;

import java.util.Optional;

import javax.swing.JComponent;

/**
 * Representation of a general view component with a child component.
 * 
 * @param <T> type of the component
 */
public interface ParentComponent<T extends JComponent> extends ViewComponent<T> {

    /**
     * Builds the component.
     * 
     * @param childComponent child component
     * @return resulting component
     */
    T build(Optional<JComponent> childComponent);

    /**
     * Builds the component without a child.
     * 
     * @return resulting component
     */
    @Override
    default T build() {
        return this.build(Optional.empty());
    }

    /**
     * Builds the component without a swing component child.
     * 
     * @param childComponent child component
     * @return resulting component
     */
    default T build(final JComponent childComponent) {
        return this.build(Optional.ofNullable(childComponent));
    }

    /**
     * Builds the component without a view component child.
     * 
     * @param childComponent child component
     * @return resulting component
     */
    default T build(final ViewComponent<?> childComponent) {
        return this.build(Optional.ofNullable(childComponent).map(ViewComponent::build));
    }

}
