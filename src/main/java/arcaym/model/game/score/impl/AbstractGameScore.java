package arcaym.model.game.score.impl;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.score.api.GameScore;

/**
 * Abstract implementation of {@link GameScore}.
 * It provides score access and initialization while leaving manipulation logic.
 */
@TypeRepresentation
public abstract class AbstractGameScore implements GameScore {

    private int value;

    /**
     * Change current value of certain amount.
     * 
     * @param amount change value
     */
    protected final void changeValue(final int amount) {
        this.value += amount;
    }

    /**
     * Increment score.
     */
    protected abstract void increment();

    /**
     * Decrement score.
     */
    protected abstract void decrement();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(
        final EventsSubscriber<GameEvent> eventsSubscriber,
        final GameStateInfo gameState
    ) {
        eventsSubscriber.registerCallback(GameEvent.INCREMENT_SCORE, e -> this.increment());
        eventsSubscriber.registerCallback(GameEvent.DECREMENT_SCORE, e -> this.decrement());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public int getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
