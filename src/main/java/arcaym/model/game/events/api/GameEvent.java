package arcaym.model.game.events.api;

/**
 * In-game events.
 */
public enum GameEvent implements Event {

    /**
     * Game ended with success.
     */
    VICTORY(0),

    /**
     * Game ended with failure.
     */
    GAME_OVER(1),

    /**
     * Game score should increment.
     */
    INCREMENT_SCORE(2),

    /**
     * Game score should decrement.
     */
    DECREMENT_SCORE(2);

    private final int priority;

    GameEvent(final int priority) {
        this.priority = priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int priority() {
        return this.priority;
    }

}
