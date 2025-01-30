package arcaym.controller.app.api;

/**
 * An interface that each sub-controller has to implement in order
 * to correctly switch from each page.
 */
public interface Controller {
    /**
     * Sets the necessary methods / objects.
     */
    void setView(/* Potentially generic stuff */);

    /**
     * Executes the given function on the return action.
     * Only temporary, will possibly be affected by future changes
     * 
     * @param onReturn The function to run
     */
    void onReturn(Runnable onReturn);

    /**
     * This function will be called if the application is closed.
     */
    void runOnClose();
}
