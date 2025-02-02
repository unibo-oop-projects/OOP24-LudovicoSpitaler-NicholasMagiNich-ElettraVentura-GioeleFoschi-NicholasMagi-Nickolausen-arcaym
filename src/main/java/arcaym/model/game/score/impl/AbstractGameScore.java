package arcaym.model.game.score.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.score.api.GameScore;

/**
 * Abstract implementation of {@link GameScore}.
 * It provides score access and initialization while leaving manipulation logic.
 */
@TypeRepresentation
public abstract class AbstractGameScore implements GameScore {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnitGameScore.class);

    private int value;

    /**
     * Change current value of certain amount.
     * 
     * @param amount change value
     */
    protected final void changeValue(final int amount) {
        final var builder = new StringBuilder("Value changed from ").append(this.value);
        this.value += amount;
        LOGGER.info(builder.append(" to ").append(this.value).toString());
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
        LOGGER.info("Successfully registered callbacks to game events");
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
