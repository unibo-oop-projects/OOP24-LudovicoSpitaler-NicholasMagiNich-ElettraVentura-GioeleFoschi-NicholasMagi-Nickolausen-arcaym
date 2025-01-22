package arcaym.view.api;

import javax.swing.JComponent;

/**
 * Representation of a general Swing view.
 * 
 * @param <T> type of the component
 */
public interface GeneralSwingView<T extends JComponent> {

    /**
     * Initializes the view and all its components.
     * 
     * @return the initialized component 
     */
    T build();
}
