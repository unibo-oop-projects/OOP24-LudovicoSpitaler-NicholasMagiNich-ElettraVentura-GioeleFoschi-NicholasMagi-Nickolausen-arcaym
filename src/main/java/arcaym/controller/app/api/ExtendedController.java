package arcaym.controller.app.api;

import arcaym.view.app.api.View;

/**
 * Interface for a {@link Controller} extended view.
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
}
