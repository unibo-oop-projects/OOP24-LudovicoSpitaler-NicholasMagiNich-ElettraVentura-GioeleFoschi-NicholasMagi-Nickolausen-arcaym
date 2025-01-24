package arcaym.view.api;

import javax.swing.JComponent;

/**
 * Representation of a general view component.
 * 
 * @param <T> type of the component
 */
public interface ViewComponent<T extends JComponent> {

    /**
     * Builds the component.
     * 
     * @return the initialized component 
     */
    T build();
}
