package arcaym.view.core.api;

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
     * @param window window info
     * @return the initialized component 
     */
    T build(WindowInfo window);
}
