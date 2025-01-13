package arcaym.controller.app.api;

/**
 * An interface exposing the method used to switch from the eidtor to
 * the playable level.
 */
public interface GameSwithcer {

    /**
     * Transforms the level from the editor to a playable one.
     * @return the controller for the level.
     */
    GameController exportToGame();
}
