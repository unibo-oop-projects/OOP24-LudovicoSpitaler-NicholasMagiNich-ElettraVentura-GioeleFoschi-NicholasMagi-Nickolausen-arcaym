package arcaym.model.game.events.api;

/**
 * In-game events.
 */
public enum GameEvent {

    /**
     * Game ended with success.
     */
    VICTORY,

    /**
     * Game ended with failure.
     */
    GAME_OVER,

    /**
     * Game score should increment.
     */
    INCREMENT_SCORE,

    /**
     * Game score should decrement.
     */
    DECREMENT_SCORE,

}
