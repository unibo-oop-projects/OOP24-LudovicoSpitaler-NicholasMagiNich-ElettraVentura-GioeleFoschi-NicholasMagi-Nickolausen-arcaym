package arcaym.controller.app.api;

import arcaym.view.app.api.View;

/**
 * Interface for a controller of a sub-section of the app.
 */
public interface Controller<T extends View> {

    /**
     * Set the view associated to this controller.
     * 
     * @param view view
     */
    void setView(T view);

    /**
     * Close the controller.
     */
    void close();

}
