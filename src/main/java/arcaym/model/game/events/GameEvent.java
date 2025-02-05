package arcaym.model.game.events;

import arcaym.model.game.core.events.Event;

/**
 * In-game events.
 */
public enum GameEvent implements Event {

    /**
     * Game ended with success.
     */
    VICTORY(0, true),

    /**
     * Game ended with failure.
     */
    GAME_OVER(1, true),

    /**
     * Game score should increment.
     */
    INCREMENT_SCORE(2),

    /**
     * Game score should decrement.
     */
    DECREMENT_SCORE(2);

    private final int priority;
    private final boolean isTerminal;

    GameEvent(final int priority, final boolean isTerminal) {
        this.priority = priority;
        this.isTerminal = isTerminal;
    }

    GameEvent(final int priority) {
        this(priority, DEFAULT_TERMINAL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int priority() {
        return this.priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminal() {
        return this.isTerminal;
    }

}
