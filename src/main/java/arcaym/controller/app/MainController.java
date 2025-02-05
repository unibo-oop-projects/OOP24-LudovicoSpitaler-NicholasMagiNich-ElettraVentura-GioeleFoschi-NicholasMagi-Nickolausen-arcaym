package arcaym.controller.app;

import arcaym.view.app.MainView;

/**
 * Interface for the main app controller.
 */
public interface MainController extends ExtendedController<MainView> { 

    /**
     * Goes back to the previous panel.
     */
    void goBack();

    /**
     * 
     * @return {@code True} if it is possible to switch to the previous
     * panel, {@code False} otherwise. 
     */
    boolean canGoBack();
}
