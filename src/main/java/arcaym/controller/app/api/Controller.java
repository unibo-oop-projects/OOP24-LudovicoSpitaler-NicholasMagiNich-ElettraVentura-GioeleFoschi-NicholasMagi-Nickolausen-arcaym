package arcaym.controller.app.api;

import arcaym.view.app.api.View;

/**
 * Interface for a generic controller.
 * 
 * @param <T> associated view type
 */
public interface Controller<T extends View> extends ControllerInfo<T> {

    /**
     * Set the view associated to this controller.
     * 
     * @param view view
     */
    void setView(T view);

    /**
     * Perform all operations needed before closing.
     */
    void dispose();

}
