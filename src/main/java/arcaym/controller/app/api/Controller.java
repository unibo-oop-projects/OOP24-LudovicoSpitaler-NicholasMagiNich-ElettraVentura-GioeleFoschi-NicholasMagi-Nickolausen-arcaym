package arcaym.controller.app.api;

import arcaym.view.app.api.View;

/**
 * Interface for a generic controller.
 * 
 * @param <T> associated view type
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
