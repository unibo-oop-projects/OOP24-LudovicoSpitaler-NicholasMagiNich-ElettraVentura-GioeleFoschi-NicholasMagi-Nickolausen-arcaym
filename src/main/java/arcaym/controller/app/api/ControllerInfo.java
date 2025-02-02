package arcaym.controller.app.api;

import arcaym.view.app.api.View;

/**
 * Interface for a {@link Controller} restricted view.
 * 
 * @param <T> associated view type
 */
public interface ControllerInfo<T extends View> {

    /**
     * Close the controller.
     */
    void close();

}
