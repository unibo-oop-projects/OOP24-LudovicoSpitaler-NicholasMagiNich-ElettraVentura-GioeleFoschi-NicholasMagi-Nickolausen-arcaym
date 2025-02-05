package arcaym.controller.app;

import arcaym.view.app.View;

/**
 * Interface for a generic extended {@link Controller}.
 * 
 * @param <T> associated view type
 */
public interface ExtendedController<T extends View> extends Controller {

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
